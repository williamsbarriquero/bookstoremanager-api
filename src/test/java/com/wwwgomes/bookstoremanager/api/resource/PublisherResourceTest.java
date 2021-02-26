package com.wwwgomes.bookstoremanager.api.resource;

import com.wwwgomes.bookstoremanager.api.builder.PublisherDTOBuilder;
import com.wwwgomes.bookstoremanager.api.dto.PublisherDTO;
import com.wwwgomes.bookstoremanager.api.service.PublisherService;
import com.wwwgomes.bookstoremanager.utils.JsonConversionUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.util.Collections;

import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class PublisherResourceTest {

    private static final String PUBLISHERS_API_URL_PATH = "/api/v1/publishers";

    private MockMvc mockMvc;

    @Mock
    private PublisherService publisherService;

    @InjectMocks
    private PublisherResource publisherResource;

    private PublisherDTOBuilder publisherDTOBuilder;

    @BeforeEach
    void setUp() {
        publisherDTOBuilder = PublisherDTOBuilder.builder().build();
        mockMvc = MockMvcBuilders.standaloneSetup(publisherResource)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .setViewResolvers((s, locale) -> new MappingJackson2JsonView())
                .build();
    }

    @Test
    void whenPOSTIsCalledThenCreatedStatusShouldBeInformed() throws Exception {
        PublisherDTO expectedCreatedPublisherDTO = publisherDTOBuilder.buildPublisherDTO();

        Mockito.when(publisherService.create(expectedCreatedPublisherDTO)).thenReturn(expectedCreatedPublisherDTO);

        mockMvc.perform(MockMvcRequestBuilders.post(PUBLISHERS_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonConversionUtils.asJsonString(expectedCreatedPublisherDTO)))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", is(expectedCreatedPublisherDTO.getId().intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", is(expectedCreatedPublisherDTO.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code", is(expectedCreatedPublisherDTO.getCode())));
    }

    @Test
    void whenPOSTIsCalledWithoutRequiredFieldsThenBadRequestStatusShouldBeInformed() throws Exception {
        PublisherDTO expectedCreatedPublisherDTO = publisherDTOBuilder.buildPublisherDTO();
        expectedCreatedPublisherDTO.setName(null);

        mockMvc.perform(MockMvcRequestBuilders.post(PUBLISHERS_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonConversionUtils.asJsonString(expectedCreatedPublisherDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenGETWithValidIdIsCalledThenOkStatusShouldBeInformed() throws Exception {
        PublisherDTO expectedCreatedPublisherDTO = publisherDTOBuilder.buildPublisherDTO();
        Long expectedCreatedPublisherDTOId = expectedCreatedPublisherDTO.getId();

        Mockito.when(publisherService.findById(expectedCreatedPublisherDTOId)).thenReturn(expectedCreatedPublisherDTO);

        mockMvc.perform(MockMvcRequestBuilders.get(PUBLISHERS_API_URL_PATH + "/" + expectedCreatedPublisherDTOId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", is(expectedCreatedPublisherDTOId.intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", is(expectedCreatedPublisherDTO.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code", is(expectedCreatedPublisherDTO.getCode())));
    }

    @Test
    void whenGETListIsCalledThenOkStatusShouldBeInformed() throws Exception {
        PublisherDTO expectedCreatedPublisherDTO = publisherDTOBuilder.buildPublisherDTO();

        Mockito.when(publisherService.findAll()).thenReturn(Collections.singletonList(expectedCreatedPublisherDTO));

        mockMvc.perform(MockMvcRequestBuilders.get(PUBLISHERS_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(expectedCreatedPublisherDTO.getId().intValue())))
                .andExpect(jsonPath("$[0].name", is(expectedCreatedPublisherDTO.getName())))
                .andExpect(jsonPath("$[0].code", is(expectedCreatedPublisherDTO.getCode())));
    }

    @Test
    void whenDELETEIsCalledThenNoContentStatusShouldBeInformed() throws Exception {
        PublisherDTO expectedPublisherToDelete = publisherDTOBuilder.buildPublisherDTO();
        var expectedPublisherIdToDelete = expectedPublisherToDelete.getId();

        doNothing().when(publisherService).delete(expectedPublisherIdToDelete);

        mockMvc.perform(delete(PUBLISHERS_API_URL_PATH + "/" + expectedPublisherIdToDelete)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}
