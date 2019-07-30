package me.depther.service.impl;

import me.depther.model.Comment;
import me.depther.model.DisplayInfoResponse;
import me.depther.model.ProductResponse;
import me.depther.repository.ProductsRepository;
import me.depther.service.ProductsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductsServiceImpl implements ProductsService {

	@Autowired
	private ProductsRepository productsRepository;

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public ProductResponse getProducts(int categoryId, int start) throws Exception {
		ProductResponse response = new ProductResponse();
		if (categoryId != 0) {
			response.setTotalCount(productsRepository.selectPartCount(categoryId));
			response.setItems(productsRepository.selectPartList(categoryId, start));
		} else {
			response.setTotalCount(productsRepository.selectAllCount());
			response.setItems(productsRepository.selectAllList(start));
		}
		return response;
	}

	@Override
	public DisplayInfoResponse getProductDetail(int displayInfoId) throws Exception {
		DisplayInfoResponse response = new DisplayInfoResponse();
		response.setDisplayInfo(productsRepository.selectDisplayInfo(displayInfoId));
		response.setProductImages(productsRepository.selectProductImages(displayInfoId));
		response.setDisplayInfoImage(productsRepository.selectDisplayInfoImage(displayInfoId));
		response.setComments(productsRepository.selectComments(displayInfoId));
		for (Comment e : response.getComments()) {
			e.setCommentImages(productsRepository.selectCommentImages(e.getCommentId()));
		}
		Double avgScore = productsRepository.selectAvgScore(displayInfoId);
		response.setAverageScore(avgScore == null ? 0 : avgScore);
		response.setProductPrices(productsRepository.selectProductPrices(displayInfoId));
		return response;
	}

}