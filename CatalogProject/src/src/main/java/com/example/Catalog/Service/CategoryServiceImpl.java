//package com.example.Catalog.Service;
//
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.mongodb.core.MongoTemplate;
//import org.springframework.data.mongodb.core.query.Criteria;
//import org.springframework.data.mongodb.core.query.Query;
//import org.springframework.stereotype.Service;
//
//import com.example.Catalog.Entity.*;
//import com.example.Catalog.Repository.CategoryRepository;
//@Service
//public class CategoryServiceImpl implements CategoryService {
//	@Autowired
//	CategoryRepository categoryRepository;
//	@Autowired
//	MongoTemplate mongoTemplate;
//	@Autowired
//	MongoTemplate mongoTemplate1;
//	public Category saveCatalog(CategoryRequest categoryRequest) {
//		Category category=null;
//		SubCategory subCategory;
//		//parent request
//		if(categoryRequest.getHierarchyLevel()==null) {
//			category=saveParent(categoryRequest);	
//		}
//		else {
//			//child request			
//			category=saveChild(categoryRequest);			
//		}
//		categoryRepository.save(category);
//		return category;
//		
//	}
//	
//	Category saveChild(CategoryRequest categoryRequest) {
//		Category category=null;
//		SubCategory subCategory=null;
//		List<SubCategory> subCategoryRequestList=new ArrayList<>();
//		String[] l=categoryRequest.getHierarchyLevel().split("-");
//		List<Integer> levels=new ArrayList<Integer>();
//		for(String a :l) {
//			//adding levels to an integer list				
//			levels.add(Integer.parseInt(a));
//		}
//		
//		
//		category=categoryRepository.findByLevelId(levels.get(0));
//		if(category.getLevels()!=null) {
//			if(levels.size()==1) {
//				subCategoryRequestList.addAll(category.getLevels());
//				//getting last level id
//				int levelRequestId=category.getLevels().get(subCategoryRequestList.size()-1).getLevelId()+1;
//				subCategoryRequestList.add(new SubCategory(levelRequestId, categoryRequest.getLevelName(), null));
//				category.setLevels(subCategoryRequestList);
//			}
//			else
//			{
//				int i=0;
//				while(i<levels.size()) 
//				{
//					System.out.println(category.getLevels());
//					subCategory=recursiveLevels(i,category,categoryRequest,levels);
//					i++;
//				}
//			}
//			
//		}
//		else {
//			subCategoryRequestList.add(new SubCategory(1, categoryRequest.getLevelName(), null));
//			category.setLevels(subCategoryRequestList);
//			
//		}
//		
//		categoryRepository.save(category);
//		return category;
//		
//	}
//	SubCategory iteratedVal=new SubCategory();
//	int counter=0;
//	List<SubCategory> catList=null;
//	List<SubCategory> catListTemp=new ArrayList<>();
//	List<SubCategory> catListTemp1=new ArrayList<>();
//	List<SubCategory> catList1=null;
//	SubCategory recursiveLevels(int i,Category category,CategoryRequest catgeoryRequest, List<Integer> levels){		
//		System.out.println("here");
//		
//		 
//		 if(counter==0) {
//			 catList =category.getLevels();
//			 catList1=new ArrayList<>();
//			 catList1.add(new SubCategory(1, catgeoryRequest.getLevelName(), null));
//			}
//		 if(i==levels.size()-1){
//			 //catList.add(new SubCategory(1,catgeoryRequest.getLevelName(), null));
//			 catListTemp1.add(iteratedVal);
//			 SubCategory temp=catListTemp.get(0);
//			 temp.setLevels(catList);
////			 catListTemp.add(iteratedVal);
//			 
////			 Remove iteratedVal from catList and add the updated one to catlist
//			 catListTemp1.add(temp) ;
//			 category.setLevels(catListTemp1);
//			// return category;
//		 }else{
//			  counter=1;
//			 i=i+1;
//			 int levelId=levels.get(i);
//			 iteratedVal=catList.stream().filter(t->t.getLevelId()==levelId).findFirst().orElse(null);
//			 
//			 if(iteratedVal.getLevels()!=null) {
//				 catListTemp.add(iteratedVal);
//				 catList.removeAll(catListTemp);
//				 catListTemp1=catList;
//				catList=iteratedVal.getLevels();
//			 }
//			 
//			 iteratedVal.setLevels(catList1);
//			 iteratedVal=recursiveLevels(i,category,catgeoryRequest,levels);
//					 }
//		 categoryRepository.save(category);
//		 return null;
//		}
//		
//
//	
//	Category saveParent(CategoryRequest categoryRequest) {
//		Category category=null;
//		List<Category> listCategory=categoryRepository.findAll();
//		category=new Category();
//		//initially if hierarchy is null
//		System.out.println("Parent Request");
//		if(listCategory.isEmpty()) {
//			category.setLevelId(1);				
//		}
//		else {
//			int nextLevelId=listCategory.get(listCategory.size()-1).getLevelId()+1;				
//			category.setLevelId(nextLevelId);				
//		}
//		category.setLevelName(categoryRequest.getLevelName());
//		category.setCreatedBy(categoryRequest.getCreatedBy());
//		category.setCreatedTime(LocalDateTime.now());
//		category.setLastUpdatedBy(categoryRequest.getLastUpdatedBy());
//		category.setLastUpdatedTime(LocalDateTime.now());
//		return category;
//	}
//}
