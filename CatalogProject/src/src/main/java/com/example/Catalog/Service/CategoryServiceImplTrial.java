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
//public class CategoryServiceImplTrial implements CategoryService {
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
//				SubCategory iteratedVal=category.getLevels();
//					subCategory=recursiveLevels(i,category,categoryRequest,levels);
//					
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
//	List<SubCategory> catList;
//	
//	List<SubCategory> temp=new ArrayList<>();
//	List<SubCategory> tempList=new ArrayList<>();
//	int counter=0;
//	Category recursiveLevels(int i,Category category,CategoryRequest newCategory,List<Integer> levels)
//	{
//	      catList=category.getLevels();
//	      if(i==levels.size()-1){
//
//	         catList.add(new SubCategory());
//	         category.setLevels(catList);
//	         return category;
//	      }else{
//	        i=i+1;
//	        int levelId=levels.get(i);
//	        iteratedVal=catList.stream().filter(t->t.getLevelId()==levelId).findFirst().orElse(null);
//
//	        iteratedVal=recursiveLevels(i,iteratedVal,newCategory,levels);
//	        catList.remove(iteratedVal);
//	        iteratedVal.setLevels()
//	        Remove iteratedVal from catList and add the updated one to catlist
//	        catList.set(iteratedVal);
//	        category.setSubCategoryList(catList);
//	      }
////	SubCategory recursiveLevels(int i,Category category,CategoryRequest catgeoryRequest, List<Integer> levels){		
////		
////		
////		if(counter==0) {
////			//getting sub levels
////			catList=category.getLevels();
////			temp.add(new SubCategory(1, catgeoryRequest.getLevelName(), null));
////		}
////		 if(i==levels.size()-1){
////			 		
////			category.setLevels(catList);
////		 }else{
////			 counter++;
////			 i=i+1;
////			 int levelId=levels.get(i);
////			 iteratedVal=catList.stream().filter(t->t.getLevelId()==levelId).findFirst().orElse(null);
////			 if(i==levels.size()-1) {
////				 catList=category.getLevels();				 
////				 catList.removeAll(tempList);
////				 tempList.remove(iteratedVal);
////				 iteratedVal.setLevels(temp);
////				 tempList.add(iteratedVal);
////				 tempList.remove(1);
////				 
////				 catList=tempList;
////				 //catList.addAll(tempList);
////			 }
////			 else {
////				 catList=iteratedVal.getLevels();
////				 
////			 }
////			 if(counter==1) {
////				 tempList.add(iteratedVal);
////				 ++counter;
////			 }
////			 iteratedVal=recursiveLevels(i,category,catgeoryRequest,levels);
////					 }
////		 categoryRepository.save(category);
////		 return null;
////		}
////		
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
