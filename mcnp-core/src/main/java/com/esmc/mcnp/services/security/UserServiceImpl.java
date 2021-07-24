package com.esmc.mcnp.services.security;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.model.security.EuUser;
import com.esmc.mcnp.repositories.security.UserRepository;

@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepo;

	private static final SecureRandom RANDOM;
	private static final int HASHING_ROUNDS = 10;

	static {
		try {
			RANDOM = SecureRandom.getInstanceStrong();
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalStateException(e);
		}
	}

	@Override
	public EuUser findByLogin(String login) {
		Optional<EuUser> opUser = userRepo.findByLogin(login);
		if (opUser.isPresent()) {
			EuUser user = opUser.get();
			user.getRoleUtilisateurs().size();
			user.getEuUtilisateur();
			return user;
		} else {
			return null;
		}
	}

	@Override
	public EuUser findById(Long id) {
		return userRepo.findOne(id.intValue());
	}

	@Override
	@Transactional(readOnly = false)
	public boolean removeUtilisateur(EuUser user) {
		try {
			userRepo.delete(user);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	@Transactional(readOnly = false)
	public boolean removeById(Long id) {
		try {
			userRepo.deleteById(id.intValue());
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	@Transactional(readOnly = false)
	public EuUser saveUser(EuUser principal, String newPassword) {
		if (newPassword != null && newPassword.length() > 0) {
			String salt = BCrypt.gensalt(HASHING_ROUNDS, RANDOM);
			principal.setHashedPassword(BCrypt.hashpw(newPassword, salt));
		}

		return this.userRepo.save(principal);
	}

	@Override
	public List<EuUser> findAll() {
		return userRepo.findAll();
	}

	@Override
	public List<EuUser> findByRoles(String role) {
		return null;
	}

	@Override
	public EuUser updateUser(EuUser user) {
		return userRepo.save(user);
	}

}
