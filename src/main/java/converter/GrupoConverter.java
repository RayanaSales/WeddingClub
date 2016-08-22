package converter;
import entidades.Grupo;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter(value = "grupoConverter")
public class GrupoConverter implements Converter
{
     @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value)
    {
        if (value != null && !value.isEmpty())
        {
            return (Grupo) component.getAttributes().get(value);
        }
        return null;
    }
    
    @Override
    public String getAsString(FacesContext context, UIComponent component, Object entity)
    {
        if (entity != null && entity instanceof Grupo)
        {
            component.getAttributes().put(((Grupo) entity).getId().toString(), entity);
            return ((Grupo) entity).getId().toString();
        }

        return null;
    }
}
