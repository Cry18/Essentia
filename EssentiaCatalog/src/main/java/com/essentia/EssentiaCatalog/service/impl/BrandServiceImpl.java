package com.essentia.essentiacatalog.service.impl;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlInOutParameter;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Service;

import com.essentia.essentiacatalog.dto.BrandDto;
import com.essentia.essentiacatalog.entity.Brand;
import com.essentia.essentiacatalog.exception.ResourceNotFoundException;
import com.essentia.essentiacatalog.repository.BrandRepository;
import com.essentia.essentiacatalog.service.BrandService;
@Service
public class BrandServiceImpl implements BrandService {

    private static final Logger logger = LogManager.getLogger(BrandServiceImpl.class);

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public BrandDto details(int id) {
        logger.debug("Fetching brand with id: {}", id);
        Brand brand = brandRepository.findById(id);
        if (brand == null) {
            logger.warn("Brand not found with id: {}", id);
            throw new ResourceNotFoundException("Brand not found");
        }
        BrandDto brandDto = new BrandDto(brand.getName(), brand.getDescription(), brand.getNazionality());
        brandDto.setId(brand.getId());
        logger.info("Brand with id: {} found", id);
        return brandDto;
    }

    @Override
    public List<BrandDto> findAllBrands() {
        logger.debug("Fetching all brands");
        List<Brand> brands = brandRepository.findAll();
        List<BrandDto> brandDtos = new ArrayList<>();
        for (Brand brand : brands) {
            BrandDto b = new BrandDto(brand.getName(),null,null);
            b.setId(brand.getId());
            brandDtos.add(b);
        }
        logger.info("Brands found");
        return brandDtos;
    }

    @Override
    public List<BrandDto> findLikeNameBrands(String name) {
        logger.debug("Fetching brands with: {} in name", name);
        List<Brand> brands = brandRepository.findLikeName(name);
        List<BrandDto> brandDtos = new ArrayList<>();
        for (Brand brand : brands) {
            BrandDto b = new BrandDto(brand.getName(),null,null);
            b.setId(brand.getId());
            brandDtos.add(b);
        }
        return brandDtos;

    }

    @Override
    public String brandProcedure(String nome, int accessiIniziali) {

        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("prova_procedura")
                .declareParameters(
                        new SqlParameter("nome", Types.VARCHAR),
                        new SqlInOutParameter("accessi", Types.INTEGER),
                        new SqlOutParameter("messaggio_saluto", Types.VARCHAR)
                );
        
        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("nome", nome)
                .addValue("accessi", accessiIniziali);

        Map<String, Object> result = jdbcCall.execute(parameters);
        
        return (String) result.get("messaggio_saluto");
    }

    @Override
    public List<BrandDto> searchBrandWithProcedure(String name){

        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("cerca_brand")
                .returningResultSet("brands", (rs, rowNum) -> {
                    BrandDto brand = new BrandDto();
                    brand.setId(rs.getInt("id"));
                    brand.setName(rs.getString("name"));
                    brand.setDescription(rs.getString("description"));
                    return brand;
                });

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("nome_parziale", name);

        Map<String, Object> result = jdbcCall.execute(params);

        List<BrandDto> brands = (List<BrandDto>) result.get("brands");
        return brands;
    }
}
