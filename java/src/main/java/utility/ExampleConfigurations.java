package utility;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import org.yaml.snakeyaml.Yaml;

import com.google.protobuf.ByteString;
import com.salesforce.eventbus.protobuf.ReplayPreset;

/**
 * The ExampleConfigurations class is used for setting up the configurations for running the examples.
 * The configurations can be read from a YAML file or created directly via an object. It also sets
 * default values when an optional configuration is not specified.
 */
public class ExampleConfigurations {

    private String subUsername;
    private String subPassword;
    private String subLoginUrl;
    private String subTenantId;
    private String subAccessToken;
    private String subHost;
    private Integer subPort;
    private String subTopic;
    private Integer subNumberOfEventsToPublish;
    private Boolean subSinglePublishRequest;
    private Integer subNumberOfEventsToSubscribeInEachFetchRequest;
    private Boolean subProcessChangedFields;
    private Boolean subPlaintextChannel;
    private Boolean subProvidedLoginUrl;
    private ReplayPreset subReplayPreset;
    private ByteString subReplayId;
    private String subManagedSubscriptionId;
    private String subDeveloperName;

    public ExampleConfigurations() {
                this(null, null, null, null, null,
                null, null, null, 5, false, 5, false,
                false, false, ReplayPreset.LATEST, null, null, null);
    }
    public ExampleConfigurations(String filename) throws IOException {

        Yaml yaml = new Yaml();
        InputStream inputStream = new FileInputStream("src/main/resources/"+filename);
        HashMap<String, Object> obj = yaml.load(inputStream);

        // Reading Required Parameters
        this.subLoginUrl = obj.get("LOGIN_URL").toString();
        this.subHost = obj.get("PUBSUB_HOST").toString();
        this.subPort = Integer.parseInt(obj.get("PUBSUB_PORT").toString());

        // Reading Optional Parameters
        this.subUsername = obj.get("USERNAME") == null ? null : obj.get("USERNAME").toString();
        this.subPassword = obj.get("PASSWORD") == null ? null : obj.get("PASSWORD").toString();
        this.subTopic = obj.get("TOPIC") == null ? "/event/Order_Event__e" : obj.get("TOPIC").toString();
        this.subTenantId = obj.get("TENANT_ID") == null ? null : obj.get("TENANT_ID").toString();
        this.subAccessToken = obj.get("ACCESS_TOKEN") == null ? null : obj.get("ACCESS_TOKEN").toString();
        this.subNumberOfEventsToPublish = obj.get("NUMBER_OF_EVENTS_TO_PUBLISH") == null ?
                5 : Integer.parseInt(obj.get("NUMBER_OF_EVENTS_TO_PUBLISH").toString());
        this.subSinglePublishRequest = obj.get("SINGLE_PUBLISH_REQUEST") == null ?
                false : Boolean.parseBoolean(obj.get("SINGLE_PUBLISH_REQUEST").toString());
        this.subNumberOfEventsToSubscribeInEachFetchRequest = obj.get("NUMBER_OF_EVENTS_IN_FETCHREQUEST") == null ?
                5 : Integer.parseInt(obj.get("NUMBER_OF_EVENTS_IN_FETCHREQUEST").toString());
        this.subProcessChangedFields = obj.get("PROCESS_CHANGE_EVENT_HEADER_FIELDS") == null ?
                false : Boolean.parseBoolean(obj.get("PROCESS_CHANGE_EVENT_HEADER_FIELDS").toString());
        this.subPlaintextChannel = obj.get("USE_PLAINTEXT_CHANNEL") != null && Boolean.parseBoolean(obj.get("USE_PLAINTEXT_CHANNEL").toString());
        this.subProvidedLoginUrl = obj.get("USE_PROVIDED_LOGIN_URL") != null && Boolean.parseBoolean(obj.get("USE_PROVIDED_LOGIN_URL").toString());

        if (obj.get("REPLAY_PRESET") != null) {
            if (obj.get("REPLAY_PRESET").toString().equals("EARLIEST")) {
                this.subReplayPreset = ReplayPreset.EARLIEST;
            } else if (obj.get("REPLAY_PRESET").toString().equals("CUSTOM")) {
                this.subReplayPreset = ReplayPreset.CUSTOM;
                this.subReplayId = getByteStringFromReplayIdInputString(obj.get("REPLAY_ID").toString());
            } else {
                this.subReplayPreset = ReplayPreset.LATEST;
            }
        } else {
            this.subReplayPreset = ReplayPreset.LATEST;
        }

        this.subDeveloperName = obj.get("MANAGED_SUB_DEVELOPER_NAME") == null ? null : obj.get("MANAGED_SUB_DEVELOPER_NAME").toString();
        this.subManagedSubscriptionId = obj.get("MANAGED_SUB_ID") == null ? null : obj.get("MANAGED_SUB_ID").toString();
    }

    public ExampleConfigurations(String username, String password, String loginUrl,
                                 String pubsubHost, int pubsubPort, String topic) {
        this(username, password, loginUrl, null, null, pubsubHost, pubsubPort, topic,
                5, false, Integer.MAX_VALUE, false, false, false, ReplayPreset.LATEST, null, null, null);
    }

    public ExampleConfigurations(String username, String password, String loginUrl, String tenantId, String accessToken,
                                 String pubsubHost, Integer pubsubPort, String topic, Integer numberOfEventsToPublish,
                                 Boolean singlePublishRequest, Integer numberOfEventsToSubscribeInEachFetchRequest,
                                 Boolean processChangedFields, Boolean plaintextChannel, Boolean providedLoginUrl,
                                 ReplayPreset replayPreset, ByteString replayId, String devName, String managedSubId) {
        this.subUsername = username;
        this.subPassword = password;
        this.subLoginUrl = loginUrl;
        this.subTenantId = tenantId;
        this.subAccessToken = accessToken;
        this.subHost = pubsubHost;
        this.subPort = pubsubPort;
        this.subTopic = topic;
        this.subSinglePublishRequest = singlePublishRequest;
        this.subNumberOfEventsToPublish = numberOfEventsToPublish;
        this.subNumberOfEventsToSubscribeInEachFetchRequest = numberOfEventsToSubscribeInEachFetchRequest;
        this.subProcessChangedFields = processChangedFields;
        this.subPlaintextChannel = plaintextChannel;
        this.subProvidedLoginUrl = providedLoginUrl;
        this.subReplayPreset = replayPreset;
        this.subReplayId = replayId;
        this.subDeveloperName = devName;
        this.subManagedSubscriptionId = managedSubId;
    }

    public String getUsername() {
        return subUsername;
    }

    public void setUsername(String subUsername) {
        this.subUsername = subUsername;
    }

    public String getPassword() {
        return subPassword;
    }

    public void setPassword(String subPassword) {
        this.subPassword = subPassword;
    }

    public String getLoginUrl() {
        return subLoginUrl;
    }

    public void setLoginUrl(String subLoginUrl) {
        this.subLoginUrl = subLoginUrl;
    }

    public String getTenantId() {
        return subTenantId;
    }

    public void setTenantId(String subTenantId) {
        this.subTenantId = subTenantId;
    }

    public String getAccessToken() {
        return subAccessToken;
    }

    public void setAccessToken(String subAccessToken) {
        this.subAccessToken = subAccessToken;
    }

    public String getPubsubHost() {
        return subHost;
    }

    public void setPubsubHost(String subHost) {
        this.subHost = subHost;
    }

    public int getPubsubPort() {
        return subPort;
    }

    public void setPubsubPort(int subPort) {
        this.subPort = subPort;
    }

    public Integer getNumberOfEventsToPublish() {
        return subNumberOfEventsToPublish;
    }

    public void setNumberOfEventsToPublish(Integer subNumberOfEventsToPublish) {
        this.subNumberOfEventsToPublish = subNumberOfEventsToPublish;
    }

    public Boolean getSinglePublishRequest() {
        return subSinglePublishRequest;
    }

    public void setSinglePublishRequest(Boolean subSinglePublishRequest) {
        this.subSinglePublishRequest = subSinglePublishRequest;
    }

    public int getNumberOfEventsToSubscribeInEachFetchRequest() {
        return subNumberOfEventsToSubscribeInEachFetchRequest;
    }

    public void setNumberOfEventsToSubscribeInEachFetchRequest(int subNumberOfEventsToSubscribeInEachFetchRequest) {
        this.subNumberOfEventsToSubscribeInEachFetchRequest = subNumberOfEventsToSubscribeInEachFetchRequest;
    }

    public Boolean getProcessChangedFields() {
        return subProcessChangedFields;
    }

    public void setProcessChangedFields(Boolean subProcessChangedFields) {
        this.subProcessChangedFields = subProcessChangedFields;
    }

    public boolean usePlaintextChannel() {
        return subPlaintextChannel;
    }

    public void setPlaintextChannel(boolean plaintextChannel) {
        this.subPlaintextChannel = plaintextChannel;
    }

    public Boolean useProvidedLoginUrl() {
        return subProvidedLoginUrl;
    }

    public String getTopic() {
        return subTopic;
    }

    public void setTopic(String subTopic) {
        this.subTopic = subTopic;
    }

    public void setProvidedLoginUrl(Boolean subProvidedLoginUrl) {
        this.subProvidedLoginUrl = subProvidedLoginUrl;
    }

    public ReplayPreset getReplayPreset() {
        return subReplayPreset;
    }

    public void setReplayPreset(ReplayPreset subReplayPreset) {
        this.subReplayPreset = subReplayPreset;
    }

    public ByteString getReplayId() {
        return subReplayId;
    }

    public void setReplayId(ByteString replayId) {
        this.subReplayId = replayId;
    }

    public String getManagedSubscriptionId() {
        return subManagedSubscriptionId;
    }

    public void setManagedSubscriptionId(String subManagedSubscriptionId) {
        this.subManagedSubscriptionId = subManagedSubscriptionId;
    }

    public String getDeveloperName() {
        return subDeveloperName;
    }

    public void setDeveloperName(String subDeveloperName) {
        this.subDeveloperName = subDeveloperName;
    }


    /**
     * NOTE: replayIds are meant to be opaque (See docs: https://developer.salesforce.com/docs/platform/pub-sub-api/guide/intro.html)
     * and this is used for example purposes only. A long-lived subscription client will use the stored replay to
     * resubscribe on failure. The stored replay should be in bytes and not in any other form.
     */
    public ByteString getByteStringFromReplayIdInputString(String input) {
        ByteString replayId;
        String[] values = input.substring(1, input.length()-2).split(",");
        byte[] b = new byte[values.length];
        int i=0;
        for (String x : values) {
            if (x.strip().length() != 0) {
                b[i++] = (byte)Integer.parseInt(x.strip());
            }
        }
        replayId = ByteString.copyFrom(b);
        return replayId;
    }
}
