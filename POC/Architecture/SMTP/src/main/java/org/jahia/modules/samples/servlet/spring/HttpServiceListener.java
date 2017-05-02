package org.jahia.modules.samples.servlet.spring;

import org.eclipse.gemini.blueprint.context.BundleContextAware;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.http.HttpService;
import org.osgi.service.http.NamespaceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;

/**
 * Service listener to make sure that we don't block startup
 */
public class HttpServiceListener implements BundleContextAware {

    public static final Logger logger = LoggerFactory.getLogger(HttpServiceListener.class);

    EmailSender emailSender;
    BundleContext bundleContext;

    public void setBundleContext(BundleContext bundleContext) {
        this.bundleContext = bundleContext;
    }

    public void setEmailSender(EmailSender emailSender) {
        this.emailSender = emailSender;
    }

    public void onBind(ServiceReference serviceReference) {
        // note : we don't use the passed service reference because it is a proxy class that we cannot use to retrieve the
        // real service object, so we simply look it up again
        ServiceReference realServiceReference = bundleContext.getServiceReference(HttpService.class.getName());
        HttpService httpService = (HttpService) bundleContext.getService(realServiceReference);
        try {
            httpService.registerServlet("/send.email", emailSender, null, null);
            logger.info("Successfully registered sender servlet at /modules/send.email");
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (NamespaceException e) {
            e.printStackTrace();
        }

    }

    public void onUnbind(ServiceReference serviceReference) {
        if (serviceReference == null) {
            return;
        }
        ServiceReference realServiceReference = bundleContext.getServiceReference(HttpService.class.getName());
        if (realServiceReference == null) {
            return;
        }
        HttpService httpService = (HttpService) bundleContext.getService(realServiceReference);
        if (httpService == null) {
            return;
        }
        httpService.unregister("/send.email");
        logger.info("Successfully unregistered sender servlet from /modules/send.email");
    }
}
