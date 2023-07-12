package br.com.dca.gestao.entities;

import br.com.dca.gestao.exceptions.InvalidDocumentTypeException;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DocumentType {
  CPF,
  CNPJ;

  public static DocumentType getDocumentType(final String documento) {

    final Integer tamanhoDocumento = documento.length();

    switch (tamanhoDocumento) {
      case 11:
        return CPF;
      case 14:
        return CNPJ;
      default:
        throw new InvalidDocumentTypeException("Tamanho do documento inválido.");
    }
  }
}
