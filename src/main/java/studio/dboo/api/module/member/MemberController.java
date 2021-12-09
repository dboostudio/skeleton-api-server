package studio.dboo.api.module.member;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import studio.dboo.api.infra.jwt.JwtFilter;
import studio.dboo.api.infra.jwt.vo.JwtToken;

import javax.validation.Valid;
import java.util.HashMap;

@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/login")
    @ApiOperation(value = "login", notes = "로그인")
    public ResponseEntity<Member> login(@RequestBody Member member){
        HashMap<Integer, JwtToken> jwtTokenHashMap = memberService.loginAndGenerateTokens(member);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtFilter.ACCESS_HEADER, "Bearer " + jwtTokenHashMap.get(0));
        httpHeaders.add(JwtFilter.REFRESH_HEADER, "Bearer " + jwtTokenHashMap.get(1));

        return ResponseEntity.status(HttpStatus.OK).headers(httpHeaders).body(member);
    }

    @PostMapping("/sign-up")
    @ApiOperation(value="sign-up", notes = "회원가입")
    public ResponseEntity<Member> signUp(@Valid @RequestBody Member member) {
        Member savedMember = memberService.signUp(member);
        return ResponseEntity.status(HttpStatus.OK).body(savedMember);
    }
}
