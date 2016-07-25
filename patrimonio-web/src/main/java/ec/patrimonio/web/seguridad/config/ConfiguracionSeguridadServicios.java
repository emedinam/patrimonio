package ec.patrimonio.web.seguridad.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

@Configuration
public class ConfiguracionSeguridadServicios {

    @Bean
    @Primary
    public RememberMeServices
        remembeMeServices(PersistentTokenRepository repo, UserDetailsService service){
            return new
                    PersistentTokenBasedRememberMeServices("1029sdnfh3jfk**/1/a",
                            service, repo);
    }

    @Bean
    public PersistentTokenRepository repository(DataSource dataSource){
        JdbcTokenRepositoryImpl tokenRepo = new JdbcTokenRepositoryImpl();
        tokenRepo.setDataSource(dataSource);
        return tokenRepo;
    }
}
