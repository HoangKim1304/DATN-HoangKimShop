package com.shoppingwithme.hoangkimshop.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import javax.transaction.Transactional;
import org.springframework.stereotype.Repository;

import com.shoppingwithme.hoangkimshop.entity.ProductEntity;

@Repository
@Transactional
public interface ProductPageableSort extends PagingAndSortingRepository<ProductEntity, Integer>{

}
