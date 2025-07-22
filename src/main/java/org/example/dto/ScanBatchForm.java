package org.example.dto;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class ScanBatchForm {

    private List<ScanForm> tasks;

}

