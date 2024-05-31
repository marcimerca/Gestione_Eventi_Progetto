package progettoSett.gestionePrenotazioni.appConfig;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class AppConfig {

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.formLogin(http->http.disable());
        httpSecurity.csrf(http->http.disable());
        httpSecurity.sessionManagement(http->http.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        httpSecurity.authorizeHttpRequests(http->http.requestMatchers("/auth/**").permitAll());
        httpSecurity.authorizeHttpRequests(http->http.requestMatchers("/utenti/**").permitAll());
        httpSecurity.authorizeHttpRequests(http->http.requestMatchers("/eventi/**").permitAll());
        httpSecurity.authorizeHttpRequests(http->http.requestMatchers("/prenotazioni/**").permitAll());
        httpSecurity.authorizeHttpRequests(http->http.requestMatchers("/utente/prenotazioni/**").permitAll());
        httpSecurity.authorizeHttpRequests(http -> http.anyRequest().authenticated());

        return httpSecurity.build();
    }

}
