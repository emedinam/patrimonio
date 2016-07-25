package ec.patrimonio.web.config;

import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EntityScan(basePackages={
        "ec.patrimonio.compartido"
})
public class ConfiguracionDatos {

}
