package com.mysite.weddingyou_backend.planner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

import java.util.Optional;
import java.util.Random;
import java.security.MessageDigest;
import java.util.Properties;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


@Service
@Transactional
public class PlannerService {
	 
	@Autowired
	private PlannerRepository plannerRepository;
	
	public void save(PlannerEntity plannerEntity) {
		//repository의 save 메소드 소환
		plannerRepository.save(plannerEntity);
		// repository의 save 메서드 호출(조건. entity 객체를 넘겨줘야 함)
	}
	
	public PlannerEntity login(PlannerEntity plannerEntity) {
		/*
		 1. 회원이 입력한 이메일로 DB에서 조회
		 2. DB에서 조회한 비밀번호와 사용자가 입력한 비밀번호가 일치하는지 판단
		 */
		Optional<PlannerEntity> planner = plannerRepository.findByEmail(plannerEntity.getEmail());
		//optional 객체가 되는거임
		
		if(planner.isPresent()) {
			PlannerEntity plannerData = planner.get(); //get해야지 plannerEntity 객체가 반환되어 저장
			//조회 결과가 있음(해당 이메일을 가진 회원 정보가 있다)
			if(plannerData.getPassword().equals(plannerEntity.getPassword())) {
				//비밀번호 일치
				return plannerData;
			}else {
				//비밀번호 불일치(로그인 실패)
				return null;
			}
		}else {
			//조회 결과가 없음(해당 이메일을 가진 회원 정보가 없다)
			return null;
		}
	}
	
	//비밀번호 변경
	public int updatePassword(String email, String password) {
		return plannerRepository.updatePassword(email, password);
	}
	
	//서비스 임시비밀번호 추가내용
	public void sendTemporaryPassword(String email) {
	    Optional<PlannerEntity> optionalPlanner = plannerRepository.findByEmail(email);
	    optionalPlanner.ifPresent(planner -> {
	        String temporaryPassword = generateTemporaryPassword();
	        planner.setPassword(temporaryPassword);
	        plannerRepository.save(planner);
	        sendEmail(email, temporaryPassword);
	    });
	    if (!optionalPlanner.isPresent()) {
	        throw new IllegalArgumentException("해당 이메일 주소로 등록된 사용자가 없습니다.");
	    }
	}

	//임시 비밀번호 생성
    private String generateTemporaryPassword() {
        int length = 10; // 임시 비밀번호의 길이 설정
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"; // 임시 비밀번호에 사용될 문자열
        StringBuilder builder = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            int randomIndex = new Random().nextInt(characters.length());
            char randomCharacter = characters.charAt(randomIndex);
            builder.append(randomCharacter);
        }

        return builder.toString();
    }

    //이메일 전송
    private void sendEmail(String email, String temporaryPassword) {
        String host = "smtp.naver.com"; // 메일 서버 호스트
        String port = "465"; // 메일 서버 포트
        String senderEmail = "your_email@naver.com"; // 보내는 사람 이메일 주소
        String senderPassword = "your_password"; // 보내는 사람 이메일 비밀번호

        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", port);
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.ssl.trust", "smtp.naver.com");

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, senderPassword);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(senderEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
            message.setSubject("임시 비밀번호 발급 안내");
            message.setText("임시 비밀번호는 " + temporaryPassword + " 입니다.");

            Transport.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("이메일 전송 중 오류가 발생했습니다.");
        }
    }
}
