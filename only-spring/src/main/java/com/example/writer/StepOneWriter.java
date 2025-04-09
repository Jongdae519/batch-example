package com.example.writer;

import com.example.domain.SampleTwo;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

@Component
public class StepOneWriter implements ItemWriter<SampleTwo> {

    @Override
    public void write(Chunk<? extends SampleTwo> chunk) throws Exception {
        System.out.println("#################################################");
        System.out.println(chunk.toString());
        System.out.println("#################################################");
    }
}
