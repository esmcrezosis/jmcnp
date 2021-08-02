package com.esmc.mcnp.infrastructure.services.security;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Optional;

import com.esmc.mcnp.domain.dto.security.User;
import com.esmc.mcnp.domain.entity.security.EuUtilisateur;
import com.esmc.mcnp.domain.entity.security.NewLocationToken;
import com.esmc.mcnp.domain.entity.security.PasswordResetToken;
import com.esmc.mcnp.domain.entity.security.VerificationToken;

public interface IUserService {

    EuUtilisateur registerNewUserAccount(User accountDto);

    EuUtilisateur getUser(String verificationToken);

    void saveRegisteredUser(EuUtilisateur user);

    void deleteUser(EuUtilisateur user);

    void createVerificationTokenForUser(EuUtilisateur user, String token);

    VerificationToken getVerificationToken(String VerificationToken);

    VerificationToken generateNewVerificationToken(String token);

    void createPasswordResetTokenForUser(EuUtilisateur user, String token);

    EuUtilisateur findUserByEmail(String email);

    PasswordResetToken getPasswordResetToken(String token);

    Optional<EuUtilisateur> getUserByPasswordResetToken(String token);

    Optional<EuUtilisateur> getUserByID(long id);

    void changeUserPassword(EuUtilisateur user, String password);

    boolean checkIfValidOldPassword(EuUtilisateur user, String password);

    String validateVerificationToken(String token);

    String generateQRUrl(EuUtilisateur user) throws UnsupportedEncodingException;

    EuUtilisateur updateUser2FA(boolean use2FA);

    List<String> getUsersFromSessionRegistry();

    NewLocationToken isNewLoginLocation(String username, String ip);

    String isValidNewLocationToken(String token);

    void addUserLocation(EuUtilisateur user, String ip);
}
