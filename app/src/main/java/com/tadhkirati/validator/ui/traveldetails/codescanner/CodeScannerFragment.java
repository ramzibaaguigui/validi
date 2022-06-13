package com.tadhkirati.validator.ui.traveldetails.codescanner;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.ScanMode;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import com.tadhkirati.validator.R;
import com.tadhkirati.validator.models.Ticket;
import com.tadhkirati.validator.ui.login.LoginUtils;
import com.tadhkirati.validator.ui.traveldetails.TravelDetailsActivity;
import com.tadhkirati.validator.ui.traveldetails.TravelDetailsView;
import com.tadhkirati.validator.ui.traveldetails.TravelDetailsViewModel;

import java.util.Collections;

public class CodeScannerFragment extends Fragment {
    public static final int CAMERA_PERMISSION_REQUEST_CODE = 30;
    private CodeScannerView codeScannerView;
    private CodeScanner codeScanner;

    private TicketPositionPasser positionPasser;

    private CodeScannerViewModel codeScannerViewModel;
    private TravelDetailsViewModel travelDetailsViewModel;

    private CodeScannerFragment(TicketPositionPasser passer) {
        this.positionPasser = passer;
    }

    public static CodeScannerFragment newInstance(TicketPositionPasser passer) {
        return new CodeScannerFragment(passer);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_code_scanner, container, false);
        initViews(view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViewModel();
        configureCodeScanner();
        observeCameraPermissionState();
        observeValidationState();
    }

    private void initViews(View view) {
        codeScannerView = view.findViewById(R.id.code_scanner_view);
    }

    private void initViewModel() {
        codeScannerViewModel = ((TravelDetailsActivity) requireActivity()).getCodeScannerViewModel();
        codeScannerViewModel.setTravelId(((TravelDetailsActivity) requireActivity()).getTravelId());
        if (requireActivity() instanceof TravelDetailsView) {
            travelDetailsViewModel = ((TravelDetailsView) requireActivity()).getTravelDetailsViewModel();
        }
    }

    private void configureCodeScanner() {
        codeScanner = new CodeScanner(requireActivity(), codeScannerView);
        codeScanner.setScanMode(ScanMode.CONTINUOUS);
        codeScanner.setFormats(Collections.singletonList(BarcodeFormat.QR_CODE));
        codeScanner.setDecodeCallback(result -> requireActivity().runOnUiThread(() -> handleDecodedQrCodeResult(result)));
    }

    @Override
    public void onResume() {
        super.onResume();
        // previes camera
        handleCodeScannerCameraPermission();
    }

    @Override
    public void onPause() {
        super.onPause();
        codeScanner.releaseResources();
    }

    private void handleDecodedQrCodeResult(Result result) {
        if (!travelDetailsViewModel.getCanScanCode()) {
            Log.i("SCAN_CODE", "IMPOSSIBLE TO SCAN");
            return;
        }
        Log.i("CODE_SCANNER", result.getText());
        // should be set again white receiving a response from
        // the server
        travelDetailsViewModel.setCanScanCode(false);
        codeScannerViewModel.setScannedTicketToken(result.getText());

        Log.i("Travel_id", String.valueOf(codeScannerViewModel.getTravelId()));
        travelDetailsViewModel.validateTicket(
                LoginUtils.formTokenHeader(requireActivity()),
                result.getText(), ((TravelDetailsActivity) requireActivity()).getTravelId(), positionPasser.getTicketPosition(result.getText()));

    }

    private void observeCameraPermissionState() {
        codeScannerViewModel.observeCameraPermissionState(requireActivity(),
                this::handleCameraPermissionStateChanged);
    }

    private void handleCameraPermissionStateChanged(Boolean permissionRequestAccepted) {
        if (permissionRequestAccepted) {
            codeScanner.startPreview();
        }
        // TODO: there are still some verifications that can be done here

    }

    private void handleCodeScannerCameraPermission() {
        if (hasAlreadyCameraPermission()) {
            codeScanner.startPreview();
            return;
        }
        if (PermissionUtils.minimumIsVersionM()) {
            requestCameraPermission();
        }
    }

    private void explainCameraPermission() {

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void requestCameraPermission() {
        requireActivity().requestPermissions(new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_REQUEST_CODE);
    }

    private boolean hasAlreadyCameraPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            return ContextCompat.checkSelfPermission(requireActivity(),
                    Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
        return false;
    }


    private boolean canRequestCameraPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return requireActivity().shouldShowRequestPermissionRationale(Manifest.permission.CAMERA);
        }
        return false;
    }


    private void observeValidationState() {
        codeScannerViewModel.observeValidationState(requireActivity(),
                new Observer<>() {
                    @Override
                    public void onChanged(Integer state) {
                        if (state == CodeScannerViewModel.STATE_VALIDATION_IN_PROGRESS) {
                            handleTicketValidationInProgress();
                            return;
                        }

                        if (state == CodeScannerViewModel.STATE_TICKET_VALIDATION_SUCCESS) {
                            handleTicketValidationSuccess();
                            return;
                        }
                        if (state == CodeScannerViewModel.STATE_VALIDATION_ERROR) {
                            handleTicketValidationError();
                            return;
                        }

                        if (state == CodeScannerViewModel.STATE_CONNECTIVITY_ERROR) {
                            handleTicketValidationConnectivityError();
                            return;
                        }



                        if (state == CodeScannerViewModel.STATE_INITIAL) {
                            handleStateInitial();
                        }

                    }

                    private void handleTicketValidationInProgress() {

                    }

                    private void handleStateInitial() {

                    }


                    private void handleTicketValidationError() {
                        // Toast.makeText(requireActivity(), "ticket validation error", Toast.LENGTH_SHORT).show();
                        Log.i("VAL_ERROR", "TIKCET VALIDATION ERROR");
                        VibrationUtils.vibrateFailed(requireActivity());
                        VibrationUtils.vibrateFailed(requireActivity());
                        // CodeScannerNotificationUtils.displayValidationError(requireActivity(),
                        // (ViewGroup) getView().getRootView());
                        Toast.makeText(requireActivity(), "TICKET VALIDATION ERROR", Toast.LENGTH_SHORT).show();

                    }

                    private void handleTicketValidationConnectivityError() {
                        // CodeScannerNotificationUtils.displayValidationError(requireActivity(), (ViewGroup) getView().getRootView());
                        Toast.makeText(requireActivity(), "ticket validation CONNECTIVITY error", Toast.LENGTH_SHORT).show();
                        VibrationUtils.vibrateFailed(requireActivity());
                    }


                    private void handleTicketValidationSuccess() {
                        // TODO: we are still neeeding to reenable scanning again;
                        VibrationUtils.vibrateSuccess(requireActivity());

                        Ticket ticket = codeScannerViewModel.getValidatedTicket();
                        // TODO: THERE IS STILL SOMETHING TO DO WITH THIS DATA
                        // Toast.makeText(requireActivity(), "could validation ticket with token: " + ticket.getToken(), Toast.LENGTH_SHORT).show();
                        Toast.makeText(requireActivity(), R.string.ticket_validated_successfully_string, Toast.LENGTH_SHORT).show();
                    }


                });

    }

    private int getInValidationTicketPosition(String token) {
        return positionPasser.getTicketPosition(token);
    }

    public interface TicketPositionPasser {
        int getTicketPosition(String token);
    }
}
