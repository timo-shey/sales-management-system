package com.example.salesmanagementsystem.dto.reporting;

import com.example.salesmanagementsystem.model.Client;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class ClientReport {
    private int totalClients;
    private List<Client> topSpendingClients;
    private int totalClientActivity;
    private String topClientLocation;
}
