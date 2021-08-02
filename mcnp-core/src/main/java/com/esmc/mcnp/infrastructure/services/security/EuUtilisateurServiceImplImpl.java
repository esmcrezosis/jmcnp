/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esmc.mcnp.infrastructure.services.security;

import java.util.List;
import java.util.Objects;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.dao.repository.security.EuUtilisateurRepository;
import com.esmc.mcnp.domain.dto.security.Login;
import com.esmc.mcnp.domain.entity.security.EuUtilisateur;
import com.esmc.mcnp.infrastructure.services.base.CrudServiceImpl;

/**
 *
 * @author USER
 */
@Service
@Transactional(readOnly = true)
public class EuUtilisateurServiceImplImpl extends CrudServiceImpl<EuUtilisateur, Long> implements EuUtilisateurService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
	private EuUtilisateurRepository userDao;
	@Autowired
	private PasswordEncoder bCryptPasswordEncoder;

	protected EuUtilisateurServiceImplImpl(EuUtilisateurRepository userDao) {
		super(userDao);
	}

	@Override
	public Login authenticateUser(String login, String password) {
		Login s_login = null;
		EuUtilisateur user = userDao.findUserByLoginAndPassword(login.trim(), password.trim());
		if (user != null) {
			s_login = new Login();
			s_login.setLogin(user.getLogin());
			s_login.setPassword("");
			s_login.setCodeGroupe(user.getEuUserGroup().getEuUserGroupParent().getCodeGroupe());
			s_login.setSousGroupe(user.getEuUserGroup().getCodeGroupe());
			s_login.setCodeMembre(user.getCodeMembre());
			s_login.setIdUserParent(user.getIdUtilisateurParent());
			s_login.setTypeUser(user.getCodeGacFiliere());
		}
		return s_login;
	}

	@Override
	public Login authenticateUser(Login c_login) {
		Login s_login = null;
		EuUtilisateur user = userDao.findUserByLoginAndPassword(c_login.getLogin(), c_login.getPassword());
		if (user != null) {
			s_login = new Login();
			s_login.setLogin(user.getLogin());
			s_login.setPassword("");
			if (Objects.nonNull(user.getEuUserGroup().getEuUserGroupParent())) {
				s_login.setCodeGroupe(user.getEuUserGroup().getEuUserGroupParent().getCodeGroupe());
			}
			s_login.setSousGroupe(user.getEuUserGroup().getCodeGroupe());
			s_login.setCodeMembre(user.getCodeMembre());
			s_login.setIdUserParent(user.getIdUtilisateurParent());
			s_login.setTypeUser(user.getCodeGacFiliere());
			s_login.setIdUtilisateur(user.getIdUtilisateur());
		}
		return s_login;
	}

	@Override
	public List<EuUtilisateur> findAllWithGroupeAndAgence() {
		return userDao.findAllWithGroupeAndAgence();
	}

	@Override
	public List<EuUtilisateur> getAll() {
		return userDao.getAllBy();
	}

	@Override
	public Page<EuUtilisateur> getAll(Pageable pageable) {
		return userDao.getAllBy(pageable);
	}

	@Override
	public List<EuUtilisateur> findByParent(Long id) {
		return userDao.findByIdUtilisateurParent(id);
	}

	@Override
	public Page<EuUtilisateur> findByParent(Long id, Pageable pageable) {
		return userDao.findByIdUtilisateurParent(id, pageable);
	}

	@Override
	public EuUtilisateur findByLogin(String login) {
		return userDao.findUserByLogin(login);
	}

	@Override
	public EuUtilisateur getOne(Long id) {
		return userDao.getByIdUtilisateur(id);
	}

	@Override
	public EuUtilisateur saveUser(EuUtilisateur principal, String newPassword) {
		if (StringUtils.isNotBlank(newPassword)) {
			principal.setPasswordHash(bCryptPasswordEncoder.encode(newPassword));
			principal.setPwd(DigestUtils.md5Hex(newPassword));
		}
		return userDao.save(principal);
	}

	@Override
	public List<EuUtilisateur> loadAll() {
		return userDao.loadAll();
	}

	@Override
	public Page<EuUtilisateur> loadAll(Pageable pageable) {
		return userDao.loadAll(pageable);
	}

	@Override
	public List<EuUtilisateur> loadAllByParent(Long id) {
		return userDao.loadAllByParent(id);
	}

	@Override
	public Page<EuUtilisateur> loadAllByParent(Long id, Pageable pageable) {
		return userDao.loadAllByParent(id, pageable);
	}

	@Override
	public EuUtilisateur findByIdUtilisateur(Long id) {
		return userDao.findByIdUtilisateur(id);
	}
}
