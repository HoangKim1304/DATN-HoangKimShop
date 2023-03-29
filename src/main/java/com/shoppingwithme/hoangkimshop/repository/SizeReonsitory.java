package com.shoppingwithme.hoangkimshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shoppingwithme.hoangkimshop.entity.SizeEntity;

@Repository
public interface SizeReonsitory extends JpaRepository<SizeEntity, Long>{

}
