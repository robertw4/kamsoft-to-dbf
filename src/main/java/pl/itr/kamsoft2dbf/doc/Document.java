package pl.itr.kamsoft2dbf.doc;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

public class Document {
    private final String docNo;
    private final String paymentDeadlineType;
    private final String documentType;
    private final Date documentDate;
    private final Date fiscalDate;
    private final Date paymentDate;
    private final String fiscal;
    private final String internalDocNo;
    private final String internalId;
    private final Amount transactionAmount;
    private final Amount retailAmount;
    private final Map<Vat, Amount> vatAmounts;
    private final Map<Vat, Amount> retailVatAmounts;
    private final BigDecimal paymentAmount;
    private final BigDecimal czNet;
    private final BigDecimal czRxNet;
    private final BigDecimal czRxwNet;
    private final BigDecimal czOtcNet;
    private final BigDecimal czOtcwNet;
    private final BigDecimal rxNet;
    private final BigDecimal rxwNet;
    private final BigDecimal otcNet;
    private final BigDecimal otcwNet;
    private String contractorName;
    private String vatId;
    private String contractorInternalId;
    private String remarks;

    public Document(
            String docNo,
            String paymentDeadlineType,
            String documentType,
            Date documentDate,
            Date fiscalDate, Date paymentDate,
            String fiscal,
            String internalDocNo,
            String internalId,
            Amount transactionAmount,
            Amount retailAmount,
            Amount purchaceAmount,
            Map<Vat, Amount> vatAmounts,
            Map<Vat, Amount> retailVatAmounts,
            BigDecimal paymentAmount,
        BigDecimal czNet,
        BigDecimal czRxNet,
        BigDecimal czRxwNet,
        BigDecimal czOtcNet,
        BigDecimal czOtcwNet,
        BigDecimal rxNet,
        BigDecimal rxwNet,
        BigDecimal otcNet,
        BigDecimal otcwNet,
            String contractorName,
            String vatId,
            String contractorInternalId,
            String remarks
    ) {
        this.docNo = docNo;
        this.paymentDeadlineType = paymentDeadlineType;
        this.documentType = documentType;
        this.documentDate = documentDate;
        this.fiscalDate = fiscalDate;
        this.paymentDate = paymentDate;
        this.fiscal = fiscal;
        this.internalDocNo = internalDocNo;
        this.internalId = internalId;
        this.transactionAmount = transactionAmount;
        this.vatAmounts = vatAmounts == null ? Map.of() : Map.copyOf(vatAmounts);
        this.retailVatAmounts = retailVatAmounts == null ? Map.of() : Map.copyOf(retailVatAmounts);
        this.paymentAmount = paymentAmount;
    this.czNet = czNet;
    this.czRxNet = czRxNet;
    this.czRxwNet = czRxwNet;
    this.czOtcNet = czOtcNet;
    this.czOtcwNet = czOtcwNet;
    this.rxNet = rxNet;
    this.rxwNet = rxwNet;
    this.otcNet = otcNet;
    this.otcwNet = otcwNet;
        this.retailAmount = isPurchaseDocument(documentType) ? retailAmount : purchaceAmount;
        this.contractorName = contractorName;
        this.vatId = vatId;
        this.contractorInternalId = contractorInternalId;
        this.remarks = remarks;
    }

    public String getContractorName() {
        return contractorName;
    }

    public String getVatId() {
        return vatId;
    }

    public String getContractorInternalId() {
        return contractorInternalId;
    }

    public String getRemarks() {
        return remarks;
    }

    public String getDocNo() {
        return docNo;
    }

    public String getPaymentDeadlineType() {
        return paymentDeadlineType;
    }

    public String getDocumentType() {
        return documentType;
    }

    public Date getDocumentDate() {
        return documentDate;
    }

    public Date getFiscalDate() {
        return fiscalDate;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public String getFiscal() {
        return fiscal;
    }

    public String getInternalDocNo() {
        return internalDocNo;
    }

    public String getInternalId() {
        return internalId;
    }

    public Optional<Amount> getTransactionAmount() {
        return Optional.ofNullable(transactionAmount);
    }

    public Optional<Amount> getRetailAmount() {
        return Optional.ofNullable(retailAmount);
    }

    public Optional<Amount> getVatAmount(Vat vat) {
        return Optional.ofNullable(vatAmounts.get(vat));
    }

    public Optional<Amount> getRetailVatAmount(Vat vat) {
        return Optional.ofNullable(retailVatAmounts.get(vat));
    }

    public Optional<BigDecimal> getPaymentAmount() {
        return Optional.ofNullable(paymentAmount);
    }

    public Optional<BigDecimal> getCzNet() {
        return Optional.ofNullable(czNet);
    }

    public Optional<BigDecimal> getCzRxNet() {
        return Optional.ofNullable(czRxNet);
    }

    public Optional<BigDecimal> getCzRxwNet() {
        return Optional.ofNullable(czRxwNet);
    }

    public Optional<BigDecimal> getCzOtcNet() {
        return Optional.ofNullable(czOtcNet);
    }

    public Optional<BigDecimal> getCzOtcwNet() {
        return Optional.ofNullable(czOtcwNet);
    }

    public Optional<BigDecimal> getRxNet() {
        return Optional.ofNullable(rxNet);
    }

    public Optional<BigDecimal> getRxwNet() {
        return Optional.ofNullable(rxwNet);
    }

    public Optional<BigDecimal> getOtcNet() {
        return Optional.ofNullable(otcNet);
    }

    public Optional<BigDecimal> getOtcwNet() {
        return Optional.ofNullable(otcwNet);
    }

    public Document setContractorName(String contractorName) {
        this.contractorName = contractorName;
        return this;
    }

    public Document setVatId(String vatId) {
        this.vatId = vatId;
        return this;
    }

    public Document setContractorInternalId(String contractorInternalId) {
        this.contractorInternalId = contractorInternalId;
        return this;
    }

    public Document setRemarks(String remarks) {
        this.remarks = remarks;
        return this;
    }

    private Boolean isPurchaseDocument(String type) {
        return switch (type) {
            case "FZV" -> true;
            case "KZV" -> true;
            default -> false;
        };
    }

    @Override
    public String toString() {
        return "Document{" +
                "contractorName='" + contractorName + '\'' +
                ", vatId='" + vatId + '\'' +
                ", docNo='" + docNo + '\'' +
                '}';
    }
}
