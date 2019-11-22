package fr.univ.orleans.wsi.tokenserver.controller;

import io.jsonwebtoken.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import services.Constantes;

import java.util.*;

import static services.Constantes.AUTHORIZATION;

@RestController
public class LoginController {

    private static Map<String, String> loginPassword = new HashMap<>();
    private static Map<String, List<String>> loginRoles = new HashMap<>();
    static {
        loginPassword.put("fred","abcd");
        loginRoles.put("fred", Arrays.asList("user","admin","supersuperadmin"));
        loginPassword.put("yo","yo");
        loginRoles.put("yo", Arrays.asList("user", "scrummaster"));
        loginPassword.put("math","math");
        loginRoles.put("math", Arrays.asList("admin","ceinturenoire"));
    }
    private static int EXP=3_600_000;
    private static String MACLE = "CACESTMACLE";
    private static String TOKEN_PREFIX="Bearer";

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String login,
                                        @RequestParam String password) {
        //TODO
        System.out.println("requete" + login + " " + password);
        String pass= loginPassword.get(login);
        if(pass != null && pass.equals(password)){
            //fabrication et envoi de token
            Claims claims = Jwts.claims().setSubject(login);
            claims.put("roles",loginRoles.get(login));
            String token = Jwts.builder()
                    .signWith(SignatureAlgorithm.HS256, MACLE.getBytes())
                    .addClaims(claims)
                    .setExpiration(new Date(System.currentTimeMillis()+EXP))
                    .compact();
            token = TOKEN_PREFIX + token;
            return ResponseEntity.ok().header(Constantes.AUTHORIZATION,token).build();
        }else{
            //reponse en induquant que la personne n'est pas authorisé
            System.out.println("ko");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @GetMapping("/checkToken")
    public ResponseEntity<String> checkToken(@RequestHeader(AUTHORIZATION)String token) {
        //TODO
        Jws<Claims> claim = Jwts.parser().setSigningKey(MACLE.getBytes()).parseClaimsJws(token.replaceFirst(TOKEN_PREFIX,""));
        String login = claim.getBody().getSubject();
        if(loginPassword.containsKey(login)){
            return ResponseEntity.ok(login);
        }else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
