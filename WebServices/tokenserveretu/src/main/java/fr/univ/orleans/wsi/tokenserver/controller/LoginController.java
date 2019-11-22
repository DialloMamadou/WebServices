package fr.univ.orleans.wsi.tokenserver.controller;

import io.jsonwebtoken.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

import static services.Constantes.AUTHORIZATION;

@RestController
@RequestMapping("/token")
public class LoginController {

    private static Map<String, String> loginPassword = new HashMap<>();
    //private static Map<String, List<String>> loginRoles = new HashMap<>();

    private static int EXP = 3_600_000;
    private static String KEY = "MYKEY";
    private static String TOKEN_PREFIX="Bearer";
    private static final String AUTHORIZATION = "Authorization";


    static {
        loginPassword.put("admin","admin");
        /*loginPassword.put("fred","abcd");
        loginRoles.put("fred", Arrays.asList("user","admin","supersuperadmin"));
        loginPassword.put("yo","yo");
        loginRoles.put("yo", Arrays.asList("user", "scrummaster"));
        loginPassword.put("math","math");
        loginRoles.put("math", Arrays.asList("admin","ceinturenoire"));*/
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<String> login(@RequestParam String login,
                                       @RequestParam String password) {
        String pwd = loginPassword.get(login);

        if(pwd != null && pwd.equals(password)){
            System.out.println("ok");
            Claims claims = Jwts.claims().setSubject(login);
            //claims.put("role",loginRoles.get(login));
            String token = Jwts.builder()
                    .signWith(SignatureAlgorithm.HS256, KEY.getBytes())
                    .addClaims(claims)
                    .setExpiration(new Date(System.currentTimeMillis()+EXP))
                    .compact();

            token = TOKEN_PREFIX + token;
            System.out.println(token);
            //return ResponseEntity.ok().header("Autorization",token).build();
            try {
                return ResponseEntity.created(new URI("/motus/connexion")).header(AUTHORIZATION,token).build();
            } catch (URISyntaxException e) {
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
        }else{
            // la personne n'est pas autorisée
            System.out.println("ko");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }


    }

    @RequestMapping(value = "/checkToken", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<String> checkToken(@RequestHeader(AUTHORIZATION) String token) {

        System.out.println("check : " + token);

        Jws<Claims> claim = Jwts.parser().setSigningKey(KEY.getBytes()).parseClaimsJws(token.replaceFirst(TOKEN_PREFIX,""));
        String login = claim.getBody().getSubject();
        System.out.println("check login decrypt: " + login);
        if(loginPassword.containsKey(login)){
            System.out.println("check : succes");
            return ResponseEntity.ok(login);
        }else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
