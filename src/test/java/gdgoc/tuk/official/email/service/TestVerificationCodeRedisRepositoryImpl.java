package gdgoc.tuk.official.email.service;

import gdgoc.tuk.official.email.repository.VerificationCodeRedisRepository;
import java.util.HashMap;
import java.util.Optional;

public class TestVerificationCodeRedisRepositoryImpl implements VerificationCodeRedisRepository {

    private final HashMap<String,String> map = new HashMap<>();

    @Override
    public void saveVerificationCode(final String email, final String code, final Integer timeout) {
        map.put(email,code);
    }

    @Override
    public Optional<Object> findVerificationCode(final String email) {
        return Optional.ofNullable(map.get(email));
    }
}
