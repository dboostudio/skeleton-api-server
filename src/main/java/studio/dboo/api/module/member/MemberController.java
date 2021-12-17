package studio.dboo.api.module.member;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import studio.dboo.api.infra.jwt.JwtFilter;
import studio.dboo.api.infra.jwt.vo.JwtToken;
import studio.dboo.api.module.member.dto.MemberLoginDTO;
import studio.dboo.api.module.member.domain.Member;
import studio.dboo.api.module.member.dto.MemberSignUpDTO;

import javax.validation.Valid;
import java.util.HashMap;

@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/login")
    @ApiOperation(value = "login", notes = "로그인")
    public ResponseEntity login(@Valid @RequestBody MemberLoginDTO dto){
        HashMap<Integer, JwtToken> jwtTokenHashMap = memberService.loginAndGenerateTokens(dto.convertToMember());

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtFilter.ACCESS_HEADER, "Bearer " + jwtTokenHashMap.get(0).getToken());
        httpHeaders.add(JwtFilter.REFRESH_HEADER, "Bearer " + jwtTokenHashMap.get(1).getToken());

        return ResponseEntity.status(HttpStatus.OK).headers(httpHeaders).build();
    }

    @PostMapping("/sign-up")
    @ApiOperation(value="sign-up", notes = "회원가입")
    public ResponseEntity<Member> signUp(@Valid @RequestBody MemberSignUpDTO signUpDTO) {
        System.out.println(signUpDTO);
        Member savedMember = memberService.signUp(signUpDTO.convertToMember());
        return ResponseEntity.status(HttpStatus.OK).body(savedMember);
    }

    @GetMapping("/my-info")
    @ApiOperation(value = "myInfo", notes = "내 정보 조회")
    public ResponseEntity<Member> getMyInfo(){
        return ResponseEntity.status(HttpStatus.OK).body(memberService.getCurrentMember());
    }
}
