package com.hocviec.auth.service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.hocviec.auth.dto.request.LoginRequest;
import com.hocviec.auth.dto.response.LoginResponse;
import com.hocviec.auth.entity.User;
import com.hocviec.auth.repository.UserRepository;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;

@Service
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${hocviec.jwt.signer-key}")
    private String SIGNER_KEY; // Đọc chuỗi bí mật từ file properties

    public AuthenticationService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public LoginResponse authenticate(LoginRequest request) {
        // 1. Tìm user trong DB theo username
        User user = userRepository.findByUsername(request.getUsername());
        if (user == null) {
            throw new RuntimeException("Tài khoản không tồn tại");
        }

        // 2. Đối chiếu mật khẩu thô gửi lên với mật khẩu đã băm trong DB
        boolean authenticated = passwordEncoder.matches(request.getPassword(), user.getPassword());
        
        if (!authenticated) {
            throw new RuntimeException("Sai mật khẩu");
        }

        // 3. Nếu đúng mật khẩu -> Tiến hành tạo JWT Token
        String token = generateToken(user);

        return LoginResponse.builder()
                .token(token)
                .authenticated(true)
                .build();
    }

    // Hàm phụ trách đúc chiếc thẻ bài JWT
    private String generateToken(User user) {
        // Cấu hình Header của JWT (Dùng thuật toán băm HS256)
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS256);

        // Cấu hình Body (Payload) chứa các thông tin (Claims)
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(user.getUsername()) // Đại diện cho định danh user
                .issuer("hocviec.com") // Nguồn phát hành token
                .issueTime(new Date()) // Thời gian phát hành
                .expirationTime(new Date(
                        Instant.now().plus(2, ChronoUnit.HOURS).toEpochMilli() // Token sống trong 2 tiếng
                ))
                .claim("fullName", user.getFullName()) // Có thể đính kèm thông tin phụ tùy ý
                .build();

        Payload payload = new Payload(jwtClaimsSet.toJSONObject());

        // Tiến hành ký tên (Sign) bằng khóa bí mật để tạo Token hoàn chỉnh
        JWSObject jwsObject = new JWSObject(header, payload);
        try {
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
            return jwsObject.serialize(); // Xuất ra chuỗi String JWT dạng: xxxxx.yyyyy.zzzzz
        } catch (JOSEException e) {
            throw new RuntimeException("Không thể tạo Token", e);
        }
    }
}