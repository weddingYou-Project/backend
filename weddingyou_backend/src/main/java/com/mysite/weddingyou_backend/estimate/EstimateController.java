package com.mysite.weddingyou_backend.estimate;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.mysite.weddingyou_backend.plannerUpdateDelete.PlannerUpdateDelete;
import com.mysite.weddingyou_backend.plannerUpdateDelete.PlannerUpdateDeleteService;
import com.mysite.weddingyou_backend.userUpdateDelete.UserUpdateDelete;
import com.mysite.weddingyou_backend.userUpdateDelete.UserUpdateDeleteService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/estimate")
public class EstimateController {

	public final EstimateService estimateService;
	
	@Autowired
	private PlannerUpdateDeleteService plannerService ;
	
	@Autowired
	private UserUpdateDeleteService userService ;
	
	@Autowired
	private EstimateRepository estimateRepository;
	
	@Value("${spring.servlet.multipart.location}")
    String uploadDir;

	@PostMapping(value = "/write", produces = "multipart/form-data")
	public void insertData(@RequestParam(value = "uploadfiles", required = false) MultipartFile[] uploadfiles,
	                       @RequestParam("weddingdate") String weddingdate,
	                       @RequestParam("budget") int budget,
	                       @RequestParam("region") String region,
	                       @RequestParam("honeymoon") String honeymoon,
	                       @RequestParam("makeup") String makeup,
	                       @RequestParam("dress") String dress,
	                       @RequestParam("requirement") String requirement,
	                       @RequestParam("studio") String studio,
	                       @RequestParam("writer") String writer)  throws IOException {
	    // 이미지 데이터 처리 로직
		List<String> list = new ArrayList<>();
		if(!(uploadfiles == null)) {
        for (MultipartFile file : uploadfiles) {
            if (!file.isEmpty()) {
                File storedFilename = new File(UUID.randomUUID().toString() + "_" + file.getOriginalFilename());
                list.add("\"" + storedFilename.toString() + "\"");
                file.transferTo(storedFilename); //업로드
            }
        }
		}
		Estimate data = new Estimate();
		data.setWeddingdate(weddingdate);
		data.setBudget(budget);
		data.setRegion(region);
		data.setHoneymoon(honeymoon);
		data.setMakeup(makeup);
		data.setDress(dress);
		data.setRequirement(requirement);
		data.setStudio(studio);
		data.setWriter(writer);
		data.setImg(list.toString());
		data.setMatchstatus(false);
		data.setTitle(writer + "님의 견적서");
		data.setDate(LocalDate.now());
		data.setViewcount(0);		
		data.setPlannermatching("[]");
		data.setUserMatching("[]");
		data.setAssigned(false);
		estimateService.insert(data);
	}
	
	//전체 데이터 조회
	@ResponseBody
	@GetMapping("/getlist")
	public ResponseEntity<List<Estimate>> getList() {
	    List<Estimate> list = estimateService.getlist();
	    return ResponseEntity.ok().body(list);
	}


	//전체 데이터 개수 조회
	@ResponseBody
	@GetMapping("/getcount")
	public ResponseEntity<Integer> getCount() {
		int count = estimateService.getcount();
		return ResponseEntity.ok().body(count);
	}
	
	//검색 데이터 조회	
	@GetMapping("/getsearchlist")
	public ResponseEntity<List<Estimate>> getsearchlist(@RequestParam String search){
		List<Estimate> list = estimateService.getsearchlist(search);
		return ResponseEntity.ok().body(list);
	}
	
	
	//이미지 출력 부분입니다.
	@RequestMapping("/imageview")
    public ResponseEntity<UrlResource> download(@RequestParam("image") String stored) throws MalformedURLException {
        UrlResource resource = new UrlResource("file:" + uploadDir + "/" + stored);
        return ResponseEntity.ok().body(resource);
    }

	
	//견적서 상세정보 조회 + 조회수 증가
	@RequestMapping("/getdetail/{id}")
	public Estimate getdetail(@PathVariable ("id") int id) {
	return estimateService.getdetail(id);
		} 
		
	
	//견적서 삭제
	@RequestMapping("/delete")
	public void delete(@RequestParam int id) {
		estimateService.delete(id);
	}
	
	
	
	//견적서 수정
	@PostMapping(value = "/modify", produces = "multipart/form-data")
	public void modifyData(@RequestParam(value = "uploadfiles", required = false) MultipartFile[] uploadfiles,
	                       @RequestParam("weddingdate") String weddingdate,
	                       @RequestParam("budget") int budget,
	                       @RequestParam("region") String region,
	                       @RequestParam("honeymoon") String honeymoon,
	                       @RequestParam("makeup") String makeup,
	                       @RequestParam("dress") String dress,
	                       @RequestParam("requirement") String requirement,
	                       @RequestParam("studio") String studio,
	                       @RequestParam("writer") String writer,  
	                       @RequestParam("previmage") String[] previmage,
	                       @RequestParam("id") long id)
	                    		   throws IOException {
	    // 이미지 데이터 처리 로직
		List<String> list = new ArrayList<>();
		for(int i = 0; i < previmage.length; i++) {
			list.add("\"" + previmage[i] + "\"");
		}
		if(!(uploadfiles == null)) {
        for (MultipartFile file : uploadfiles) {
            if (!file.isEmpty()) {
                File storedFilename = new File(UUID.randomUUID().toString() + "_" + file.getOriginalFilename());
                list.add("\"" + storedFilename.toString() + "\"");
                file.transferTo(storedFilename); //업로드
            }
        }
		}
		Estimate data = new Estimate();
		data.setWeddingdate(weddingdate);
		data.setBudget(budget);
		data.setRegion(region);
		data.setHoneymoon(honeymoon);
		data.setMakeup(makeup);
		data.setDress(dress);
		data.setRequirement(requirement);
		data.setStudio(studio);
		data.setWriter(writer);
		data.setImg(list.toString());
		data.setMatchstatus(false);
		data.setTitle(writer + "님의 견적서");
		data.setDate(LocalDate.now());
		data.setViewcount(0);		
		data.setId(id);
		estimateService.insert(data);
	}
	
	//견적서 매칭원하는 플래너 삽입하기
		@PostMapping(value = "/insert/matchingplanner")
		public void updateData(
		                       @RequestParam("id") Long id,
								@RequestParam("plannermatching") String plannermatching)
		                    		   throws Exception {
		    
			Estimate targetData = estimateService.getEstimateDetail(id);
			
			System.out.println("targetData.plannermatching:"+targetData.getPlannermatching());
			JSONParser parser = new JSONParser();
			ArrayList<String> obj = (ArrayList<String>) parser.parse(plannermatching);
			ArrayList<String> plannerList = null;
			if(targetData.getPlannermatching()!=null) {
				plannerList = (ArrayList<String>) parser.parse(targetData.getPlannermatching());
				System.out.println(plannerList);
			}
			 
			
			if(plannerList.size() == 0) {
				Estimate data = new Estimate();
				data.setPlannermatching(plannermatching);
				targetData.setPlannermatching(data.getPlannermatching());
				
				estimateService.save(targetData);
			}
			else if(plannerList.size()!=0 && !plannerList.containsAll(obj)) {
				Estimate data = new Estimate();
				data.setPlannermatching(plannermatching);
				targetData.setPlannermatching(data.getPlannermatching());
				
				estimateService.save(targetData);
			}else if(plannerList.size()!=0  && plannerList.containsAll(obj)){
				throw new Exception("중복됩니다!");
			}
			
		}
	
		//견적서 매칭원하는 플래너 삽입하기
				@GetMapping(value = "/getuserdetail")
				public List<Estimate> getUserDetail(@RequestParam("userEmail") String userEmail) throws Exception {
				    
					List<Estimate> targetData = estimateService.getEstimateDetailByEmail(userEmail);
					if(targetData!=null) {
						return targetData;
					}
					else {
						throw new Exception("정보가 존재하지 않습니다!");
					}
				
				}
			
			
				//견적서 매칭원하는 플래너 이름 가져오기
				@GetMapping(value = "/getPlannerName")
				public ArrayList<ArrayList<String>> getPlannerName(@RequestParam("userEmail") String userEmail) throws Exception {
				    
					List<Estimate> targetData = estimateService.getEstimateDetailByEmail(userEmail);
					if(targetData!=null) {
						ArrayList<ArrayList<String>> result = new ArrayList<>();
						for(int i =0;i<targetData.size();i++) {
							String plannerArr = targetData.get(i).getPlannermatching();
						    JSONParser parser = new JSONParser();
						    ArrayList<String> obj = (ArrayList<String>) parser.parse(plannerArr); 
						    ArrayList<String> temp = new ArrayList<>();
							for(int j = 0;j<obj.size();j++) {
								
								PlannerUpdateDelete data = plannerService.getPlannerByEmail(obj.get(j));
								
								temp.add(data.getName());
//								temp.concat(eachPlannerName);
//								if(j!=obj.size()-1) {
//									temp.concat(",");
//								}
								System.out.println(temp);
							}
							result.add(temp);
						}
						
						return result;
					}
					else {
						throw new Exception("정보가 존재하지 않습니다!");
					}
				
				}
				
				//견적서 매칭원하는 플래너 이름 삭제하기
				@PostMapping(value = "/deleteMatchingPlanner")
				public int deleteMatchingPlanner(@RequestParam("deletePlanner") String deletePlanner, 
						@RequestParam("deleteTargetEstimateId") Long estimateId) throws Exception {
				    int res = 0;
				    
					Estimate targetEstimate = estimateService.getEstimateDetail(estimateId);
					
					Estimate newEstimate = new Estimate();
					System.out.println(targetEstimate.getPlannermatching());
				    JSONParser parser = new JSONParser();
				    ArrayList<String> obj = (ArrayList<String>) parser.parse(targetEstimate.getPlannermatching());
				    obj.remove(deletePlanner);
				   
				    if(targetEstimate.isMatchstatus()) {
				    	ArrayList<String> obj2 = (ArrayList<String>) parser.parse(targetEstimate.getUserMatching());
						obj2.remove(deletePlanner);
						 targetEstimate.setUserMatching(String.valueOf(obj2));
				    	targetEstimate.setMatchstatus(false);
				    	res=2;
				    }
				    newEstimate.setPlannermatching(String.valueOf(obj));
				    targetEstimate.setPlannermatching(String.valueOf(obj));
				   
				    estimateService.save(targetEstimate);
					
					return res;
				
				}
				
				//매칭하기
				@PostMapping(value = "/matching")
				public String matchingPlanner(@RequestParam("matchingPlanner") String matchingPlanner, 
						@RequestParam("targetEstimateId") Long estimateId, @RequestParam("userEmail") String userEmail
						) throws Exception {
				    String result="";
				    List<Estimate> targetData = estimateService.getEstimateDetailByEmail(userEmail);
					Estimate targetEstimate = estimateService.getEstimateDetail(estimateId);
					for(int i=0;i<targetData.size();i++) {
						targetData.get(i).setAssigned(false);
					}
//					for(int i=0;i<targetData.size();i++) {
						ArrayList<String> cleanList= new ArrayList<>();
						Estimate cleanEstimate = targetEstimate;
						cleanEstimate.setPlannermatching(String.valueOf(cleanList));
						cleanEstimate.setUserMatching(String.valueOf(cleanList));
						cleanEstimate.setAssigned(true);
						estimateService.save(cleanEstimate);
//					}
				
					System.out.println(targetEstimate.getPlannermatching());
					
					
					JSONParser parser = new JSONParser();
					ArrayList<String> obj = (ArrayList<String>) parser.parse(targetEstimate.getPlannermatching());
				    obj.add(matchingPlanner);
					targetEstimate.setPlannermatching(String.valueOf(obj));
					targetEstimate.setUserMatching(String.valueOf(obj));
					targetEstimate.setMatchstatus(true);
				    estimateService.save(targetEstimate);
				    
					UserUpdateDelete data = userService.getUserByEmail(userEmail);
					PlannerUpdateDelete plannerData = plannerService.getPlannerByEmail(matchingPlanner);
					
					System.out.println(data.getName());
					String userName = data.getName()+"/";
					result= userName;
					System.out.println(data.getPhoneNum());
					String userPhone = data.getPhoneNum()+"]";
					result+= userPhone;

					String plannerEmail = plannerData.getEmail()+"[";
					result +=plannerEmail;
					String plannerName = plannerData.getName()+",";
					result+=plannerName;
					String price = targetEstimate.getBudget()+"*";
					result+=price;
				         
				    try {
				    	if(plannerData.getPlannerImg()!=null) {
				    		Path imagePath = Paths.get("C:/Project/profileImg/planner",plannerData.getPlannerImg());
					        byte[] imageBytes = Files.readAllBytes(imagePath);
					        byte[] base64encodedData = Base64.getEncoder().encode(imageBytes);
					        result += String.valueOf(new String(base64encodedData));
				    	}
				    	
				       
				    } catch (IOException e) {
				           e.printStackTrace();
				        
				    }
				    

					System.out.println("result"+result);
					return result;
				
				}
				
				//매칭된 플래너 정보 가져오기
				@PostMapping(value = "/getMatchedPlanner")
				public String matchingPlanner(@RequestParam("userEmail") String userEmail
						) throws Exception {
				    
				    List<Estimate> targetData = estimateService.getEstimateDetailByEmail(userEmail);
				    String matchedPlanner ="";
				    int estimateNum = 0;
				    String result = "";
					for(int i=0;i<targetData.size();i++) {
						Boolean matchStatus = targetData.get(i).isMatchstatus();
						Boolean assigned = targetData.get(i).getAssigned();
						if(matchStatus) {
							JSONParser parser = new JSONParser();
							ArrayList<String> obj = (ArrayList<String>) parser.parse(targetData.get(i).getPlannermatching());
							matchedPlanner = obj.get(0);
							estimateNum = i+1;
							System.out.println(estimateNum);
							PlannerUpdateDelete data = plannerService.getPlannerByEmail(matchedPlanner);
							String plannerName = data.getName();
							result += plannerName + "/" + String.valueOf(estimateNum)+"|";
						}
					}
					System.out.println(result);
					
					
					return result;
				
				}
				
				//매칭취소하기
				@PostMapping(value = "/cancelMatchedPlanner")
				public int cancelMatchedPlanner(@RequestParam("userEmail") String userEmail
						,@RequestParam("deleteTargetEstimateId") Long EstimateId
						) throws Exception {
				    
					 List<Estimate> targetData = estimateService.getEstimateDetailByEmail(userEmail);
					 
					    int res = 0;

						Estimate targetEstimate = estimateService.getEstimateDetail(EstimateId);
						ArrayList<String> cleanList= new ArrayList<>();
						targetEstimate.getPlannermatching();
						targetEstimate.setPlannermatching(String.valueOf(cleanList));
						targetEstimate.setUserMatching(String.valueOf(cleanList));
						targetEstimate.setMatchstatus(false);
						estimateService.save(targetEstimate);
						
//						for(int i=0;i<targetData.size();i++) {
//						
//							Boolean matchStatus = targetData.get(i).isMatchstatus();
//							if(matchStatus) {
//								ArrayList<String> cleanList= new ArrayList<>();
//								Estimate cleanEstimate = targetData.get(i);
//								cleanEstimate.setPlannermatching(String.valueOf(cleanList));
//								cleanEstimate.setMatchstatus(false);
//								estimateService.save(cleanEstimate);
//								res = 1;
//								break;
//							}
//						}
						res = 1;
						
						return res;
				
				}
				
				//매칭취소하기
				@PostMapping(value = "/cancelMatchedPlanner2")
				public int cancelMatchedPlanner2(@RequestParam("userEmail") String userEmail
						,@RequestParam("estimateNum") int estimateNum
						) throws Exception {
				    
					 List<Estimate> targetData = estimateService.getEstimateDetailByEmail(userEmail);
					 
					    int res = 0;

					    Boolean matchStatus = targetData.get(estimateNum).isMatchstatus();
						if(matchStatus) {
							ArrayList<String> cleanList= new ArrayList<>();
							Estimate cleanEstimate = targetData.get(estimateNum);
							cleanEstimate.setPlannermatching(String.valueOf(cleanList));
							cleanEstimate.setUserMatching(String.valueOf(cleanList));
							cleanEstimate.setMatchstatus(false);
							estimateService.save(cleanEstimate);
							res = 1;
						}
						
						
						return res;
				
				}
				
				//플래너 요청, 고객요청 비교해서 서로 짝 찾기
				@PostMapping(value = "/findMatching")
				public List<String> findMatching(@RequestParam("email") String email
						,@RequestParam("category") String category
						) throws Exception {
					List<String> result = new ArrayList<>();
					if(category.equals("user")) {
						 
						List<Estimate> estimatesData = estimateService.getEstimateDetailByEmail(email);
						System.out.println(estimatesData.size());
						if(estimatesData!=null) {
							int k=0;
							for(int i =0;i<estimatesData.size();i++) {
								Estimate targetEstimate = estimatesData.get(i);
								JSONParser parser = new JSONParser();
								
								ArrayList<String> plannermatching = (ArrayList<String>) parser.parse(targetEstimate.getPlannermatching());
								ArrayList<String> usermatching = (ArrayList<String>) parser.parse(targetEstimate.getUserMatching());
								ArrayList<String> originPlannerMatching = (ArrayList<String>) parser.parse(targetEstimate.getPlannermatching());
								JSONArray temp = new JSONArray();
								if(plannermatching.size()!=0 && usermatching.size() !=0) {
									System.out.println(plannermatching);
									System.out.println(usermatching);
									
									System.out.println(originPlannerMatching.size());
									plannermatching.retainAll(usermatching);
									int m =0;
								
									if(plannermatching.size()!=0) {
										
										for(int l=0;l<originPlannerMatching.size();l++) {
											String str = originPlannerMatching.get(l);
											if(usermatching.contains(str)) {
												temp.add(str);
											
											}else {
												temp.add("empty");
												
											}
										}
									
										System.out.println("temp:"+temp);
									}
									
									
									if(plannermatching.size()!=0) {
										
										result.add(String.valueOf(temp));
										result.add(String.valueOf(k));
										
									}else {
										result.add(String.valueOf(new ArrayList<>()));
										result.add(String.valueOf(0));
										k++;
										continue;
									}
									
								}else {
									result.add(String.valueOf(new ArrayList<>()));
									result.add(String.valueOf(0));
								}
								
								k++;
							}
						}
						
					}else if(category.equals("planner")) {
						List<Estimate> estimatesData1 = estimateRepository.findAll();
					
						if(estimatesData1!=null) {
							int k= 0;
							for(int i =0;i<estimatesData1.size();i++) {
								Estimate targetEstimate = estimatesData1.get(i);
							
								JSONParser parser = new JSONParser();
								
								ArrayList<String> plannermatching = (ArrayList<String>) parser.parse(targetEstimate.getPlannermatching());
								ArrayList<String> usermatching = (ArrayList<String>) parser.parse(targetEstimate.getUserMatching());
								ArrayList<String> originUserMatching = usermatching;
								if(usermatching.contains(email)) {
									if(plannermatching.size()!=0 && usermatching.size() !=0) {
										if(plannermatching.contains(email) && usermatching.contains(email)) {
											System.out.println(plannermatching);
											System.out.println(usermatching);
											result.add(String.valueOf(k));
										}else {
											result.add(String.valueOf(-1));
										}
										
									}
									k++;
									
								}
							
								
							}
						}
					}
					return result;
				
				}

	
}


