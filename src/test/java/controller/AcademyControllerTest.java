package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.teachAssistantHelper.MyApplication;
import com.teachAssistantHelper.dto.academy.AcademyRequestDto;
import com.teachAssistantHelper.dto.academy.AcademyResponseDto;
import com.teachAssistantHelper.service.AcademyService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = MyApplication.class)
@AutoConfigureMockMvc
class AcademyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AcademyService academyService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("학원 생성 성공")
    void createAcademy() throws Exception {
        // given
        AcademyRequestDto request = new AcademyRequestDto();
        request.getClass().getDeclaredField("name").set(request, "코드학원");
        request.getClass().getDeclaredField("address").set(request, "서울시 강남구");
        request.getClass().getDeclaredField("tel").set(request, "010-1234-5678");

        AcademyResponseDto response = new AcademyResponseDto(
                new com.teachAssistantHelper.domain.Academy(1L, "코드학원", "서울시 강남구", "010-1234-5678")
        );

        given(academyService.create(any())).willReturn(response);

        // when & then
        mockMvc.perform(post("/api/academies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("코드학원"))
                .andExpect(jsonPath("$.address").value("서울시 강남구"))
                .andExpect(jsonPath("$.tel").value("010-1234-5678"));
    }

    @Test
    @DisplayName("학원 전체 조회")
    void getAllAcademies() throws Exception {
        // given
        List<AcademyResponseDto> list = List.of(
                new AcademyResponseDto(new com.teachAssistantHelper.domain.Academy(1L, "A학원", "서울", "010-1111-1111")),
                new AcademyResponseDto(new com.teachAssistantHelper.domain.Academy(2L, "B학원", "부산", "010-2222-2222"))
        );

        given(academyService.findAll()).willReturn(list);

        // when & then
        mockMvc.perform(get("/api/academies"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("A학원"))
                .andExpect(jsonPath("$[1].address").value("부산"));
    }

    @Test
    @DisplayName("학원 수정 성공")
    void updateAcademy() throws Exception {
        // given
        AcademyRequestDto request = new AcademyRequestDto();
        request.getClass().getDeclaredField("name").set(request, "수정학원");
        request.getClass().getDeclaredField("address").set(request, "수정시 수정구");
        request.getClass().getDeclaredField("tel").set(request, "010-0000-0000");

        AcademyResponseDto response = new AcademyResponseDto(
                new com.teachAssistantHelper.domain.Academy(1L, "수정학원", "수정시 수정구", "010-0000-0000")
        );

        given(academyService.update(eq(1L), any())).willReturn(response);

        // when & then
        mockMvc.perform(put("/api/academies/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("수정학원"));
    }

    @Test
    @DisplayName("학원 삭제 성공")
    void deleteAcademy() throws Exception {
        // given
        doNothing().when(academyService).delete(1L);

        // when & then
        mockMvc.perform(delete("/api/academies/1"))
                .andExpect(status().isOk());
    }
}
