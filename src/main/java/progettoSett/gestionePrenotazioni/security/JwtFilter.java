package progettoSett.gestionePrenotazioni.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.UnavailableException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;
import progettoSett.gestionePrenotazioni.exceptions.UnauthorizedException;
import progettoSett.gestionePrenotazioni.model.Utente;
import progettoSett.gestionePrenotazioni.service.UtenteService;

import java.io.IOException;
import java.util.Optional;

@Component
public class JwtFilter extends OncePerRequestFilter {
    @Autowired
    private JwtTool jwtTool;

    @Autowired
    private UtenteService utenteService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");

        if(authHeader== null || !authHeader.startsWith("Bearer ")){
            throw  new UnauthorizedException("Errore nell'autorizzazione, token mancante!");
        }

        String token = authHeader.substring(7);


         try{
             jwtTool.verifyToken(token);
             int utenteId = jwtTool.getIdFromToken(token);
             Optional<Utente> utenteOptional = utenteService.getUtenteById(utenteId);

             if(utenteOptional.isPresent()){
                 Utente utente = utenteOptional.get();
                 Authentication authentication = new UsernamePasswordAuthenticationToken(utente,null,utente.getAuthorities());

                 SecurityContextHolder.getContext().setAuthentication(authentication);

             } else {
                 throw new UnauthorizedException("Utente non trovato nel token");
             }

         } catch (Exception e){
             throw new UnauthorizedException("Errore nell'autorizzazione" + e.getMessage());
         }

        filterChain.doFilter(request,response);

    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return new AntPathMatcher().match("/auth/**", request.getServletPath());
    }
}
