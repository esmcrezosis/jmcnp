package com.esmc.mcnp.infrastructure.services.security;

import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.URLEncoder;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.esmc.mcnp.dao.repository.security.EuUtilisateurRepository;
import com.esmc.mcnp.dao.repository.security.NewLocationTokenRepository;
import com.esmc.mcnp.dao.repository.security.PasswordResetTokenRepository;
import com.esmc.mcnp.dao.repository.security.UserLocationRepository;
import com.esmc.mcnp.dao.repository.security.VerificationTokenRepository;
import com.esmc.mcnp.domain.dto.security.User;
import com.esmc.mcnp.domain.entity.security.EuUtilisateur;
import com.esmc.mcnp.domain.entity.security.NewLocationToken;
import com.esmc.mcnp.domain.entity.security.PasswordResetToken;
import com.esmc.mcnp.domain.entity.security.UserLocation;
import com.esmc.mcnp.domain.entity.security.VerificationToken;
import com.esmc.mcnp.commons.exception.security.UserAlreadyExistException;
import com.maxmind.geoip2.DatabaseReader;

@Service
@Transactional
public class UserService implements IUserService {

    @Autowired
    private EuUtilisateurRepository userRepository;

    @Autowired
    private VerificationTokenRepository tokenRepository;

    @Autowired
    private PasswordResetTokenRepository passwordTokenRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private SessionRegistry sessionRegistry;

    @Autowired
    @Qualifier("GeoIPCountry")
    private DatabaseReader databaseReader;

    @Autowired
    private UserLocationRepository userLocationRepository;

    @Autowired
    private NewLocationTokenRepository newLocationTokenRepository;

    @Autowired
    private Environment env;

    public static final String TOKEN_INVALID = "invalidToken";
    public static final String TOKEN_EXPIRED = "expired";
    public static final String TOKEN_VALID = "valid";

    public static String QR_PREFIX = "https://chart.googleapis.com/chart?chs=200x200&chld=M%%7C0&cht=qr&chl=";
    public static String APP_NAME = "SpringRegistration";

    // API

    @Override
    public EuUtilisateur registerNewUserAccount(final User accountDto) {
        if (emailExists(accountDto.getEmail())) {
            throw new UserAlreadyExistException("There is an account with that email address: " + accountDto.getEmail());
        }
        final EuUtilisateur user = new EuUtilisateur();
        user.setPwd(passwordEncoder.encode(accountDto.getPwd()));
        user.setEmail(accountDto.getEmail());
        user.setUsing2FA(accountDto.getUsing2FA());
        return userRepository.save(user);
    }

    @Override
    public EuUtilisateur getUser(final String verificationToken) {
        final VerificationToken token = tokenRepository.findByToken(verificationToken);
        if (token != null) {
            return token.getUser();
        }
        return null;
    }

    @Override
    public VerificationToken getVerificationToken(final String VerificationToken) {
        return tokenRepository.findByToken(VerificationToken);
    }

    @Override
    public void saveRegisteredUser(final EuUtilisateur user) {
        userRepository.save(user);
    }

    @Override
    public void deleteUser(final EuUtilisateur user) {
        final VerificationToken verificationToken = tokenRepository.findByUser(user);

        if (verificationToken != null) {
            tokenRepository.delete(verificationToken);
        }

        final PasswordResetToken passwordToken = passwordTokenRepository.findByUser(user);

        if (passwordToken != null) {
            passwordTokenRepository.delete(passwordToken);
        }

        userRepository.delete(user);
    }

    @Override
    public void createVerificationTokenForUser(final EuUtilisateur user, final String token) {
        final VerificationToken myToken = new VerificationToken(token, user);
        tokenRepository.save(myToken);
    }

    @Override
    public VerificationToken generateNewVerificationToken(final String existingVerificationToken) {
        VerificationToken vToken = tokenRepository.findByToken(existingVerificationToken);
        vToken.updateToken(UUID.randomUUID()
            .toString());
        vToken = tokenRepository.save(vToken);
        return vToken;
    }

    @Override
    public void createPasswordResetTokenForUser(final EuUtilisateur user, final String token) {
        final PasswordResetToken myToken = new PasswordResetToken(token, user);
        passwordTokenRepository.save(myToken);
    }

    @Override
    public EuUtilisateur findUserByEmail(final String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public PasswordResetToken getPasswordResetToken(final String token) {
        return passwordTokenRepository.findByToken(token);
    }

    @Override
    public Optional<EuUtilisateur> getUserByPasswordResetToken(final String token) {
        return Optional.ofNullable(passwordTokenRepository.findByToken(token) .getUser());
    }

    @Override
    public Optional<EuUtilisateur> getUserByID(final long id) {
        return userRepository.findById(id);
    }

    @Override
    public void changeUserPassword(final EuUtilisateur user, final String password) {
        user.setPwd(passwordEncoder.encode(password));
        userRepository.save(user);
    }

    @Override
    public boolean checkIfValidOldPassword(final EuUtilisateur user, final String oldPassword) {
        return passwordEncoder.matches(oldPassword, user.getPwd());
    }

    @Override
    public String validateVerificationToken(String token) {
        final VerificationToken verificationToken = tokenRepository.findByToken(token);
        if (verificationToken == null) {
            return TOKEN_INVALID;
        }

        final EuUtilisateur user = verificationToken.getUser();
        final Calendar cal = Calendar.getInstance();
        if ((verificationToken.getExpiryDate()
            .getTime() - cal.getTime()
            .getTime()) <= 0) {
            tokenRepository.delete(verificationToken);
            return TOKEN_EXPIRED;
        }

        user.setEnabled(true);
        // tokenRepository.delete(verificationToken);
        userRepository.save(user);
        return TOKEN_VALID;
    }

    @Override
    public String generateQRUrl(EuUtilisateur user) throws UnsupportedEncodingException {
        return QR_PREFIX + URLEncoder.encode(String.format("otpauth://totp/%s:%s?secret=%s&issuer=%s", APP_NAME, user.getLogin(), user.getQuestionSecrete(), APP_NAME), "UTF-8");
    }

    @Override
    public EuUtilisateur updateUser2FA(boolean use2FA) {
        final Authentication curAuth = SecurityContextHolder.getContext()
            .getAuthentication();
        EuUtilisateur currentUser = (EuUtilisateur) curAuth.getPrincipal();
        currentUser.setUsing2FA(use2FA);
        currentUser = userRepository.save(currentUser);
        final Authentication auth = new UsernamePasswordAuthenticationToken(currentUser, currentUser.getPwd(), curAuth.getAuthorities());
        SecurityContextHolder.getContext()
            .setAuthentication(auth);
        return currentUser;
    }

    private boolean emailExists(final String email) {
        return userRepository.findByEmail(email) != null;
    }

    @Override
    public List<String> getUsersFromSessionRegistry() {
        return sessionRegistry.getAllPrincipals()
            .stream()
            .filter((u) -> !sessionRegistry.getAllSessions(u, false)
                .isEmpty())
            .map(o -> {
                if (o instanceof EuUtilisateur) {
                    return ((EuUtilisateur) o).getEmail();
                } else {
                    return o.toString()
            ;
                }
            }).collect(Collectors.toList());
    }

    @Override
    public NewLocationToken isNewLoginLocation(String username, String ip) {

        if(!isGeoIpLibEnabled()) {
            return null;
        }

        try {
            final InetAddress ipAddress = InetAddress.getByName(ip);
            final String country = databaseReader.country(ipAddress)
                .getCountry()
                .getName();
            System.out.println(country + "====****");
            final EuUtilisateur user = userRepository.findByEmail(username);
            final UserLocation loc = userLocationRepository.findByCountryAndUser(country, user);
            if ((loc == null) || !loc.isEnabled()) {
                return createNewLocationToken(country, user);
            }
        } catch (final Exception e) {
            return null;
        }
        return null;
    }

    @Override
    public String isValidNewLocationToken(String token) {
        final NewLocationToken locToken = newLocationTokenRepository.findByToken(token);
        if (locToken == null) {
            return null;
        }
        UserLocation userLoc = locToken.getUserLocation();
        userLoc.setEnabled(true);
        userLoc = userLocationRepository.save(userLoc);
        newLocationTokenRepository.delete(locToken);
        return userLoc.getCountry();
    }

    @Override
    public void addUserLocation(EuUtilisateur user, String ip) {

        if(!isGeoIpLibEnabled()) {
            return;
        }

        try {
            final InetAddress ipAddress = InetAddress.getByName(ip);
            final String country = databaseReader.country(ipAddress)
                .getCountry()
                .getName();
            UserLocation loc = new UserLocation(country, user);
            loc.setEnabled(true);
            userLocationRepository.save(loc);
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }

    private boolean isGeoIpLibEnabled() {
        return Boolean.parseBoolean(env.getProperty("geo.ip.lib.enabled"));
    }

    private NewLocationToken createNewLocationToken(String country, EuUtilisateur user) {
        UserLocation loc = new UserLocation(country, user);
        loc = userLocationRepository.save(loc);

        final NewLocationToken token = new NewLocationToken(UUID.randomUUID()
            .toString(), loc);
        return newLocationTokenRepository.save(token);
    }
}
