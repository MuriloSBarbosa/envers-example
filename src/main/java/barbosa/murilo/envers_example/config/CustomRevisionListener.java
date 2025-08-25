package barbosa.murilo.envers_example.config;

import barbosa.murilo.envers_example.entity.RevisionInfoEntity;
import java.util.Optional;
import org.hibernate.envers.RevisionListener;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class CustomRevisionListener implements RevisionListener {

    @Override
    public void newRevision(Object revisionEntity) {
        RevisionInfoEntity rev = (RevisionInfoEntity) revisionEntity;

        // Set IP address
        Optional.ofNullable(((ServletRequestAttributes) RequestContextHolder
                    .getRequestAttributes()))
              .map(ServletRequestAttributes::getRequest)
              .ifPresent(request -> {
                  rev.setIpAddress(request.getRemoteAddr());
                  rev.setRequestUri(request.getRequestURI());
                  rev.setHttpMethod(request.getMethod());
              });
    }
}
