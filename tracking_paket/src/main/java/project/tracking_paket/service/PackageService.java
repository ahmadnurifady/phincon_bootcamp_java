package project.tracking_paket.service;

import java.util.List;

import project.tracking_paket.dto.request.PackageRequest;
import project.tracking_paket.dto.response.PackageResponse;
import project.tracking_paket.model.PackageModel;




public interface PackageService {
    PackageResponse save(PackageRequest request);

    List<PackageModel> findAll();

    PackageModel findPackageById(String id);
}
