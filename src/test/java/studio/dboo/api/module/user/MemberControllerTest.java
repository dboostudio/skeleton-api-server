package studio.dboo.api.module.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import studio.dboo.api.forTest.MockMvcTest;
import studio.dboo.api.module.user.dto.UserSignUp;

import javax.transaction.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("local")
@MockMvcTest
class MemberControllerTest {

    @Autowired MockMvc mockMvc;
    @Autowired ObjectMapper objectMapper;

    @Test
    @DisplayName("회원가입-정상")
    @Transactional // 실제 DB에 플러쉬 되지 않는다.
    public void signUp() throws Exception {
        UserSignUp memberSignUpDTO = UserSignUp.builder()
                .loginId("dboo.studio_")
                .password("eoghks1@!2_")
                .build();
        mockMvc.perform(post("/api/member/sign-up")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(memberSignUpDTO)))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("회원가입-비정상 : 로그인 아이디 BLANK 이거나 포함")
    public void signUpErrorIdBlank() throws Exception {
        UserSignUp memberSignUpDTO1 = UserSignUp.builder()
                .loginId(" ")
                .password("eoghks1@!2_")
                .build();
        UserSignUp memberSignUpDTO2 = UserSignUp.builder()
                .loginId(" aa")
                .password("eoghks1@!2_")
                .build();
        mockMvc.perform(post("/api/member/sign-up")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(memberSignUpDTO1)))
                .andExpect(status().isBadRequest())
                .andDo(print());
        mockMvc.perform(post("/api/member/sign-up")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(memberSignUpDTO2)))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    @DisplayName("회원가입-비정상 : 로그인 아이디 50자 초과")
    public void signUpErrorIdExceed() throws Exception {
        UserSignUp memberSignUpDTO = UserSignUp.builder()
                .loginId("dboostudiodboostudiodboostudiodboostudiodboostudiodboostudiodboostudiodboostudiodboostudiodboostudiodboostudio")
                .password("eoghks1@!2_")
                .build();
        mockMvc.perform(post("/api/member/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(memberSignUpDTO)))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    @DisplayName("회원가입-비정상 : 로그인 아이디 패턴")
    public void signUpErrorIdPattern() throws Exception {
        UserSignUp memberSignUpDTO = UserSignUp.builder()
                .loginId("dboo.studio!@#$")
                .password("eoghks1@!2_")
                .build();
        mockMvc.perform(post("/api/member/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(memberSignUpDTO)))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }


    @Test
    @DisplayName("회원가입-비정상 : 패스워드 BLANK")
    public void signUpErrorPwdBlank() throws Exception {
        UserSignUp memberSignUpDTO = UserSignUp.builder()
                .loginId("dboo.studio")
                .password(" ")
                .build();
        mockMvc.perform(post("/api/member/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(memberSignUpDTO)))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

}