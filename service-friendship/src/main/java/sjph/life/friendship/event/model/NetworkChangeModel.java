package sjph.life.friendship.event.model;

/**
 * @author Shaohui Guo
 *
 */
public class NetworkChangeModel {

    private String type;
    private String action;
    private String networkId;
    private String correlationId;

    /**
     * 
     */
    public NetworkChangeModel() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @param type
     * @param action
     * @param networkId
     * @param correlationId
     */
    public NetworkChangeModel(String type, String action, String networkId, String correlationId) {
        super();
        this.type = type;
        this.action = action;
        this.networkId = networkId;
        this.correlationId = correlationId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getNetworkId() {
        return networkId;
    }

    public void setNetworkId(String networkId) {
        this.networkId = networkId;
    }

    public String getCorrelationId() {
        return correlationId;
    }

    public void setCorrelationId(String correlationId) {
        this.correlationId = correlationId;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((action == null) ? 0 : action.hashCode());
        result = prime * result + ((correlationId == null) ? 0 : correlationId.hashCode());
        result = prime * result + ((networkId == null) ? 0 : networkId.hashCode());
        result = prime * result + ((type == null) ? 0 : type.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        NetworkChangeModel other = (NetworkChangeModel) obj;
        if (action == null) {
            if (other.action != null)
                return false;
        }
        else if (!action.equals(other.action))
            return false;
        if (correlationId == null) {
            if (other.correlationId != null)
                return false;
        }
        else if (!correlationId.equals(other.correlationId))
            return false;
        if (networkId == null) {
            if (other.networkId != null)
                return false;
        }
        else if (!networkId.equals(other.networkId))
            return false;
        if (type == null) {
            if (other.type != null)
                return false;
        }
        else if (!type.equals(other.type))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "NetworkChangeModel [type=" + type + ", action=" + action + ", networkId="
                + networkId + ", correlationId=" + correlationId + "]";
    }
}
