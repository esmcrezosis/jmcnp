package com.kreatech.config.thread;

import java.io.File;
import java.util.Date;
import java.util.TimerTask;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kreatech.api.module.sys.service.ISysOperationLogService;
import com.kreatech.api.module.sys.service.impl.SysloginRecordServiceImpl;
import com.kreatech.api.util.SpringUtils;
import com.kreatech.common.constant.Constants;
import com.kreatech.common.util.DateUtils;
import com.kreatech.common.util.IpUtil;
import com.kreatech.common.util.LogUtils;
import com.kreatech.common.util.ServletUtils;
import com.kreatech.data.entity.sys.SysLoginRecord;
import com.kreatech.data.entity.sys.SysOperationLog;

import eu.bitwalker.useragentutils.UserAgent;

/**
 * Usine asynchrone (pour générer des tâches)
 *
 */
public class AsyncFactory {
	private static final Logger sys_user_logger = LoggerFactory.getLogger("sys-user");

	/**
	 * Enregistrement du journal des opérations
	 *
	 * @param operLog informations du journal des opérations
	 * @return Tâche de tâche
	 */
	public static TimerTask recordOper(final SysOperationLog operLog) {
		return new TimerTask() {
			@Override
			public void run() {
				// Emplacement de l'opération de requête à distance
				operLog.setLocation(IpUtil.getRealAddressByIP(operLog.getIp()));
				SpringUtils.getBean(ISysOperationLogService.class).save(operLog);
			}
		};
	}

	/**
    * Enregistrer les informations de connexion
    *
    * @param nom d'utilisateur nom d'utilisateur
    * @param Statut 
    * @param Message 
    * @param Liste d'arguments 
    * @return Tâche de tâche 
    */
    public static TimerTask recordLogininfor(final String username, final String status, final String message, final Object... args)
    {
        final UserAgent userAgent = UserAgent.parseUserAgentString(ServletUtils.getRequest().getHeader("User-Agent"));
        final String ip = ServletUtils.getIp();
        return new TimerTask()
        {
            @Override
            public void run()
            {
                String address = IpUtil.getRealAddressByIP(ip);
                StringBuilder s = new StringBuilder();
                s.append(LogUtils.getBlock(ip));
                s.append(address);
                s.append(LogUtils.getBlock(username));
                s.append(LogUtils.getBlock(status));
                s.append(LogUtils.getBlock(message));
                // Imprimer les informations pour se connecter
                sys_user_logger.info(s.toString(), args);
                // Obtenez le système d'exploitation client
                String os = userAgent.getOperatingSystem().getName();
                // Obtenez le navigateur client
                String browser = userAgent.getBrowser().getName();
                // Objet de package
                SysLoginRecord logininfor = new SysLoginRecord();
                logininfor.setLoginName(username);
                logininfor.setIp(ip);
                logininfor.setLocation(address);
                logininfor.setBrowser(browser);
                logininfor.setOs(os);
                logininfor.setMsg(message);
                // État du journal
                if (StringUtils.equalsAny(status, Constants.LOGIN_SUCCESS, Constants.LOGOUT, Constants.REGISTER))
                {
                    logininfor.setStatus(Constants.SUCCESS);
                }
                else if (Constants.LOGIN_FAIL.equals(status))
                {
                    logininfor.setStatus(Constants.FAIL);
                }
                // Insérer des données
                SpringUtils.getBean(SysloginRecordServiceImpl.class).create(logininfor);
            }
        };
    }

	/**
	 * Nettoyer les fichiers de sauvegarde de base de données expirés
	 * 
	 * @param jours  Si 30 est passé, cela signifie nettoyer les fichiers il y a 30
	 *               jours
	 * @param Chemin du dossier du dossier pour stocker les fichiers de sauvegarde
	 * @return
	 */
	public static TimerTask cleanOutDateBackupFile(final Integer days, final String folder) {
		return new TimerTask() {
			@Override
			public void run() {
				File file = new File(folder);
				File[] files = file.listFiles();
				Date now = new Date();
				Date daysAgo = DateUtils.addDays(now, days > 0 ? -days : days);
				String name = "";
				for (File f : files) {
					// Jugez la date par le nom du fichier tel que ry_2019_10_19_11_34_943.sql
					name = f.getName();
					name = name.replace("ry_", "");
					name = name.substring(0, 10);
					name = name.replace("_", "-");
					Date date;
					date = DateUtils.parseDate(name);
					if (date != null && date.before(daysAgo)) {
						if (f.exists()) {
							f.delete();
						}
					}
				}
			}
		};
	}
}
