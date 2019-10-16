package com.ndovel.ebook.service;

import com.ndovel.ebook.model.dto.SpiderInfoDTO;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface SpiderInfoService {

    List<SpiderInfoDTO> findAll();

    Page<SpiderInfoDTO> find(Integer index, Integer size);

    Page<SpiderInfoDTO> find(Boolean finished, Integer index, Integer size);

    Optional<SpiderInfoDTO> findIsExist(Integer id);

    Integer finishSpider(Integer id);

    Integer continueSpider(Integer id);

    SpiderInfoDTO save(Integer id, String url, Integer matchRexId);
}
