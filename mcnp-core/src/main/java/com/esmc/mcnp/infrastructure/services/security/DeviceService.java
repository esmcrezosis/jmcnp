package com.esmc.mcnp.infrastructure.services.security;

import static java.util.Objects.nonNull;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

import com.maxmind.geoip2.exception.AddressNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.esmc.mcnp.dao.repository.cm.EuMembreMoraleRepository;
import com.esmc.mcnp.dao.repository.cm.EuMembreRepository;
import com.esmc.mcnp.dao.repository.security.DeviceMetadataRepository;
import com.esmc.mcnp.domain.entity.cm.EuMembre;
import com.esmc.mcnp.domain.entity.cm.EuMembreMorale;
import com.esmc.mcnp.domain.entity.security.DeviceMetadata;
import com.esmc.mcnp.domain.entity.security.EuUtilisateur;
import com.google.common.base.Strings;
import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CityResponse;

import ua_parser.Client;
import ua_parser.Parser;

@Component
public class DeviceService {

    private static final String UNKNOWN = "UNKNOWN";

    @Value("${support.email}")
    private String from;

    private Environment env;
    private DeviceMetadataRepository deviceMetadataRepository;
    private EuMembreRepository membreRepository;
    private EuMembreMoraleRepository moraleRepository;
    private DatabaseReader databaseReader;
    private Parser parser;
    private JavaMailSender mailSender;
    private MessageSource messages;

    public DeviceService(DeviceMetadataRepository deviceMetadataRepository,
                         EuMembreRepository membreRepository,
                         EuMembreMoraleRepository moraleRepository,
                         @Qualifier("GeoIPCity") DatabaseReader databaseReader,
                         Parser parser, Environment env,
                         JavaMailSender mailSender,
                         MessageSource messages) {
        this.deviceMetadataRepository = deviceMetadataRepository;
        this.membreRepository = membreRepository;
        this.moraleRepository = moraleRepository;
        this.databaseReader = databaseReader;
        this.parser = parser;
        this.env = env;
        this.mailSender = mailSender;
        this.messages = messages;
    }

    public void verifyDevice(EuUtilisateur user, HttpServletRequest request) throws IOException, GeoIp2Exception {

        String ip = extractIp(request);
        String location = getIpLocation(ip);

        String deviceDetails = getDeviceDetails(request.getHeader("user-agent"));

        DeviceMetadata existingDevice = findExistingDevice(user.getIdUtilisateur(), deviceDetails, location);

        if (Objects.isNull(existingDevice)) {
            if (notifyNewLocation()) {
                if (StringUtils.isNotBlank(user.getCodeMembre())) {
                    if (user.getCodeMembre().endsWith("P")) {
                        EuMembre membre = membreRepository.findOne(user.getCodeMembre());
                        unknownDeviceNotification(deviceDetails, location, ip, membre.getEmailMembre(), request.getLocale());
                    } else {
                        EuMembreMorale morale = moraleRepository.findOne(user.getCodeMembre());
                        unknownDeviceNotification(deviceDetails, location, ip, morale.getEmailMembre(), request.getLocale());
                    }
                }
            }

            DeviceMetadata deviceMetadata = new DeviceMetadata();
            deviceMetadata.setUserId(user.getIdUtilisateur());
            deviceMetadata.setLocation(location);
            deviceMetadata.setDeviceDetails(deviceDetails);
            deviceMetadata.setLastLoggedIn(new Date());
            deviceMetadataRepository.save(deviceMetadata);
        } else {
            existingDevice.setLastLoggedIn(new Date());
            deviceMetadataRepository.save(existingDevice);
        }

    }

    private String extractIp(HttpServletRequest request) {
        String clientIp;
        String clientXForwardedForIp = request.getHeader("x-forwarded-for");
        if (nonNull(clientXForwardedForIp)) {
            clientIp = parseXForwardedHeader(clientXForwardedForIp);
        } else {
            clientIp = request.getRemoteAddr();
        }

        return clientIp;
    }

    private String parseXForwardedHeader(String header) {
        return header.split(" *, *")[0];
    }

    private String getDeviceDetails(String userAgent) {
        String deviceDetails = UNKNOWN;

        Client client = parser.parse(userAgent);
        if (Objects.nonNull(client)) {
            deviceDetails = client.userAgent.family + " " + client.userAgent.major + "." + client.userAgent.minor +
                    " - " + client.os.family + " " + client.os.major + "." + client.os.minor;
        }

        return deviceDetails;
    }

    private String getIpLocation(String ip) throws IOException, GeoIp2Exception {
        String location = UNKNOWN;
        InetAddress ipAddress = InetAddress.getByName(ip);
        try {
            CityResponse cityResponse = databaseReader.city(ipAddress);
            if (Objects.nonNull(cityResponse) &&
                    Objects.nonNull(cityResponse.getCity()) &&
                    !Strings.isNullOrEmpty(cityResponse.getCity().getName())) {

                location = cityResponse.getCity().getName();
            }
        } catch (AddressNotFoundException e) {

        }
        return location;
    }

    private DeviceMetadata findExistingDevice(Long userId, String deviceDetails, String location) {

        List<DeviceMetadata> knownDevices = deviceMetadataRepository.findByUserId(userId);

        for (DeviceMetadata existingDevice : knownDevices) {
            if (existingDevice.getDeviceDetails().equals(deviceDetails) &&
                    existingDevice.getLocation().equals(location)) {
                return existingDevice;
            }
        }

        return null;
    }

    private void unknownDeviceNotification(String deviceDetails, String location, String ip, String email, Locale locale) {
        final String subject = "New Login Notification";
        final SimpleMailMessage notification = new SimpleMailMessage();
        notification.setTo(email);
        notification.setSubject(subject);

        String text = getMessage("message.login.notification.deviceDetails", locale) +
                " " + deviceDetails +
                "\n" +
                getMessage("message.login.notification.location", locale) +
                " " + location +
                "\n" +
                getMessage("message.login.notification.ip", locale) +
                " " + ip;

        notification.setText(text);
        notification.setFrom(from);

        mailSender.send(notification);
    }

    private String getMessage(String code, Locale locale) {
        try {
            return messages.getMessage(code, null, locale);

        } catch (NoSuchMessageException ex) {
            return messages.getMessage(code, null, Locale.ENGLISH);
        }
    }

    private boolean notifyNewLocation() {
        return Boolean.parseBoolean(env.getProperty("geo.new.location.notification"));
    }

}
