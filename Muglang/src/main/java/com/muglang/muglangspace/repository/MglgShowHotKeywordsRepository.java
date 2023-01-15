package com.muglang.muglangspace.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.muglang.muglangspace.entity.MglgShowHotKeywords;

@Transactional
public interface MglgShowHotKeywordsRepository extends JpaRepository<MglgShowHotKeywords, Integer> {

}
