package br.com.ecommerce.strategy;

public class FreteExpresso implements CalculadoraFrete {

    @Override
    public double calcular(String cep) {
        if (cep == null || cep.isEmpty()) return 45.0;

        String prefixo = cep.substring(0, 2);

        if (prefixo.equals("01") || prefixo.equals("02") || prefixo.equals("03")) {
            return 25.0;  // SP capital
        } else if (prefixo.equals("8") || prefixo.equals("9")) {
            return 60.0;  // Sul
        }
        return 35.0;  // padrão
    }
}