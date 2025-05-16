package demo.producer.service;

import org.springframework.stereotype.Service;

@Service
public class FakeSideEffectService {

    public void doSomething() {
        System.out.println("Doing something...");
    }
}
