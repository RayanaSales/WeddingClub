package excecao;

import javax.ejb.ApplicationException;

@ApplicationException(rollback = true)
public class ExcecaoNegocio extends Exception
{
    private String chave;
    public static final String OBJETO_INEXISTENTE = "excecao.ExcecaoNegocio.objetoInexistente";
    public static final String OBJETO_EXISTENTE = "excecao.ExcecaoNegocio.objetoExistente";

    public static final String TELEFONE_ASSOCIADO = "excecao.ExcecaoNegocio.telefoneServico.remover";
    public static final String ROUPA_ASSOCIADO = "excecao.ExcecaoNegocio.roupaServico.remover";
    public static final String PRESENTE_ASSOCIADO = "excecao.ExcecaoNegocio.presenteServico.remover";
    public static final String LOCAL_ASSOCIADO = "excecao.ExcecaoNegocio.localServico.remover";
    public static final String COMESBEBES_ASSOCIADO = "excecao.ExcecaoNegocio.comesBebesServico.remover";
    public static final String BUFFET_ASSOCIADO = "excecao.ExcecaoNegocio.buffetServico.remover";
    
    public ExcecaoNegocio(String chave)
    {
        this.chave = chave;
    }

    public String getChave()
    {
        return chave;
    }

    @Override
    public String getMessage()
    {
        MensagemExcecao mensagemExcecao = new MensagemExcecao(this);
        return mensagemExcecao.getMensagem();
    }
}
