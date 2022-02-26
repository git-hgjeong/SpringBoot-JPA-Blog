package com.cos.blog.test;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;

@RestController
public class DummyController {

	@Autowired
	private UserRepository userRepository;
	
	@GetMapping("/dummy/user/{id}")
	public User detail(@PathVariable int id) {
		
		//1) 이 방식은 null이 리턴될수 있다. 예외처리가 필요하다.
		//User user = userRepository.findById(id).get();
		
		//2) 빈 객체를 리턴한다. 이 방법도 그다지 적절하지 않다.
//		User user = userRepository.findById(id).orElseGet(new Supplier<User>() {
//			@Override
//			public User get() {
//				// TODO Auto-generated method stub
//				return new User();
//			}
//		});

		//3) 권장하는 방법. IllegalArgumentException을 사용한다. 에러화면은 추후 AOP를 사용하여 별도 처리한다.
//		User user = userRepository.findById(id).orElseThrow(new Supplier<IllegalArgumentException>() {
//			@Override
//			public IllegalArgumentException get() {
//				// TODO Auto-generated method stub
//				return new IllegalArgumentException("해당 유저가 존재하지 않습니다!");
//			}
//		});		

		//4) 편한 방법. 람다식을 사용하고 IllegalArgumentException을 사용한다. 에러화면은 추후 AOP를 사용하여 별도 처리한다.
		User user = userRepository.findById(id).orElseThrow(()-> {
			return new IllegalArgumentException("해당 유저가 존재하지 않습니다. ID : "+ id);
		});				
		
		return user;
	}
	
	@GetMapping("/dummy/users")
	public List<User> list(){
		List<User> list = userRepository.findAll();
		return list;
	}
	
	//http://localhost:8000/blog/dummy/user/list?page=0
	@GetMapping("/dummy/user/list")
	public List<User> pageList(@PageableDefault(size=2, sort="id", direction=Sort.Direction.DESC) Pageable pageable){
		
		Page<User> pagingUsers = userRepository.findAll(pageable);
		List<User> list = pagingUsers.getContent();
		
		return list;
	}	
	
	@PostMapping("/dummy/join")
	public String join(User user) {
		System.out.println("User : "+ user.toString());
		user.setRole(RoleType.USER);
		userRepository.save(user);		
		return "회원가입 완료.";
	}	
	
	//MessageConverter의 Jackson 라이브러리가 RequestBody로 넘어온 내용을 Java객체로 자동 변환해줌.
	//save함수는 id를 전달하지 않으면 insert를 해주고
	//save함수는 id를 전달하면 해당 id에 대한 데이터가 있으면 update를 해주고
	//save함수는 id를 전달하면 해당 id에 대한 데이터가 없으면 insert를 한다.
	@Transactional	//함수가 종료되면 자동 Commit. 이때 영속성 영역에 변경된 객체들을 DB와 동기화(Insert/Update) 한다.
	@PutMapping("/dummy/user/{id}")
	public User updateUser(@PathVariable int id, @RequestBody User requestUser) {
		
		System.out.println("id : "+ id);
		System.out.println(requestUser.toString());
		
		//1) User Object가 영속화됨.
		User user = userRepository.findById(id).orElseThrow(()-> {
			return new IllegalArgumentException("수정할 데이터가 존재하지 않습니다. id:"+ id);
		});
		
		//2) 영속화된 User Object를 변경하게 되면 Transaction이 종료될때 변경을 감지하여 DB에 수정을 처리함.=> 더티체킹. 
		user.setPassword(requestUser.getPassword());
		user.setEmail(requestUser.getEmail());
		
		//userRepository.save(user);	//@Transactional 처리하면 생략가능. 더티체킹에 의하여 자동 save 처리됨.
		
		return user;
	}
	
	@DeleteMapping("/dummy/user/{id}")
	public String deletUser(@PathVariable int id) {
		try {
			userRepository.deleteById(id);
		}catch(EmptyResultDataAccessException e) {
			return "삭제할 데이터가 존재하지 않습니다. ID : "+ id;
		}
		return "삭제되었습니다.";
	}
}
