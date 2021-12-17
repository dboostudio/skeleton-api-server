package studio.dboo.api.module.user;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
public class UserController {

//    private final MemberService memberService;
//
//    @PostMapping("/login")
//    @ApiOperation(value = "login", notes = "로그인")
//    public ResponseEntity login(@Valid @RequestBody UserLogin dto){
//        HashMap<Integer, AuthToken> jwtTokenHashMap = memberService.loginAndGenerateTokens(dto.convertToMember());
//
//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.add(JwtFilter.ACCESS_HEADER, "Bearer " + jwtTokenHashMap.get(0).getToken());
//        httpHeaders.add(JwtFilter.REFRESH_HEADER, "Bearer " + jwtTokenHashMap.get(1).getToken());
//
//        return ResponseEntity.status(HttpStatus.OK).headers(httpHeaders).build();
//    }
//
//    @PostMapping("/sign-up")
//    @ApiOperation(value="sign-up", notes = "회원가입")
//    public ResponseEntity<User> signUp(@Valid @RequestBody UserSignUp signUpDTO) {
//        System.out.println(signUpDTO);
//        User savedMember = memberService.signUp(signUpDTO.convertToMember());
//        return ResponseEntity.status(HttpStatus.OK).body(savedMember);
//    }
//
//    @GetMapping("/my-info")
//    @ApiOperation(value = "myInfo", notes = "내 정보 조회")
//    public ResponseEntity<User> getMyInfo(){
//        return ResponseEntity.status(HttpStatus.OK).body(memberService.getCurrentMember());
//    }
}
