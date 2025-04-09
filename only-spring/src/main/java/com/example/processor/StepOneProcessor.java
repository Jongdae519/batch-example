package com.example.processor;

import com.example.domain.SampleOne;
import com.example.domain.SampleTwo;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class StepOneProcessor implements ItemProcessor<SampleOne, SampleTwo> {

    @Override
    public SampleTwo process(SampleOne item) throws Exception {
        return new SampleTwo(item.id(), item.name() + "2");
    }
}
