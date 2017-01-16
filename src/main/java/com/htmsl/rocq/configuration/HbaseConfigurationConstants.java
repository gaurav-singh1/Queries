package com.htmsl.rocq.configuration;




public class HbaseConfigurationConstants {

	public static class tableNames{
		public final static String APPINFO_TABLE= "appinfo";
		public final static String PUSHINFO_TABLE = "pushinfoNew";
		public final static String PUSHREQRES_TABLE = "pushreqres";
		public final static String CAMPAIGNINFO_TABLE= HBaseConfigurationConstansts.testTables?"campaignInfoTestNew":"campaignInfo";
		public final static String CAMPAIGNINFOPENDING_TABLE=HBaseConfigurationConstansts.testTables?"campaignInfoPendingTest":"campaignInfoPendingNew";
		public final static String SCHEDULED_CAMPAIGNINFO_TABLE= HBaseConfigurationConstansts.testTables?"scheduledCampaignInfoTest":"scheduledCampaignInfo";
		public final static String CAMPAIGNPUSHRES_TABLE = "campaignPushResponse";
		public final static String SHINE_CAMPAIGNINFO_TABLE="campaignInfo_Shine";
		public final static String ARCHIVE_PUSHINFO_TABLE = "pushInfoArchive";
		public final static String NEWKEYS_PUSHINFO_TABLE = "pushInfoNewKeys";
		public final static String ImportedPushInfoTable = "importedPushKeysInfo";
		public final static String ImportedPushKeysTable = "importedPushKeysList";
		public final static String PUSHKEYS_TABLE = "pushkeyslist";
		public final static String PUSH_KEYS_TABLE = PUSHKEYS_TABLE;
		public final static String APILOG_TABLE= "apiLog";
		public final static String DYN_TABLE= "dynsegpush";
		public static final String API_KEYS_TABLE = "apikeyslist";
	}

	public static class ValidationConstants{


		public final static char[] HEX = {
				'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'
		};
		public final static String HASH_SIGNATURE="hs";
		public static final String REQUEST_TIMESTAMP="rt";
		public static final String HMAC_SHA1_ALGORITHM = "HmacSHA1";
		public static final String IP = "ip";
		public static final String KEY = "key";
		public static final String REQUEST_COUNT="rc";
		public static final long REQUEST_ALLOWED= 10000;
		public static final int TIMESTAMP_INTERVAL=15;
		public static final String PLATFORM_DASHBOARD = "sparq_dashboard";
		public static final String PLATFORM_API = "sparq_api";
		public final static String VERSION_PARAMETER = "version";
		public static final String VERSION_BASIC = "1.0.0";

	}

	public static class ColumnKeys {
		public final static String PUSHINFO_COLUMNFAMILY = "f1";
		public final static String ARCHIVE_PUSHINFO_COLUMNFAMILY = "f1";
		public final static String PUSHREQRES_COLUMNFAMILY = "f2";
		public final static String APPINFO_COLUMNFAMILY = "f3";
		public final static String CAMPAIGNINFO_COLUMNFAMILY = "f3";
		public final static String SCHEDULED_CAMPAIGNINFO_COLUMNFAMILY = "f3";
		public final static String CAMPAIGNPUSHRES_COLUMNFAMILY = "f2";
		public final static String NEWKEYS_PUSHINFO_COLUMNFAMILY = "f1";
		public final static String PUSHKEYS_PUSHINFO_COLUMNFAMILY = "f1";
		public final static String DYN_SEG_FAMILY = "dyncolfam";
		public final static String API_KEY_FAMILY = "f1";
		public final static String API_KEY_COL = "pk";
		public final static String DYN_SEG_COL = "value";


		public final static String APP_SECRET="App";
		public final static String APP_NAME = "AppName";
		public final static String APP_IMG_PERMISSION = "AppPerm";
		public final static String APP_API_KEY="AppAPI";
		public final static String APP_RESTRICTED_PACKAGE_NAME = "pkg";
		public final static String OS_PLATFORM = "OS";
		public final static String KEYSTORE_PASSWORD = "kp";
		public final static String KEYSTORE_PROD_PASSWORD = "pp";
		public final static String KEYSTORE_DEV_PASSWORD = "dp";
		public final static String PRODUCTION_PARAMETER= "p";
		public final static String VERSION = "v";
		public final static String ROW_KEY="rk";
		public final static String IMPORTED_ROW_KEY="PushInfoRowKey";
		public final static String OVERRIDE_ID = "ck";
		public final static String CAMPAIGN_REPORTS = "cr";

		public final static String PUSH_KEY = "PushKey";
		public final static String USER_ID = "UserId";
		public final static String STATUS = "Status";
		public final static String DEVICE_TIMESTAMP = "DeviceTimestamp";
		public final static String INITIAL_DEVICE_TIMESTAMP="InitialTimeStamp";
		public final static String ENTRY_TIMESTAMP = "t";
		public final static String PUSH_REQUEST_TIMESTAMP = "PushReqTimestamp";
		public final static String LAST_PUSH_REQUEST_TIMESTAMP = "lastPush";
		public final static String PUSHINFO_SDK_TIMESTAMP_UPDATE = "lastResponseTimestamp";
		public final static String DEVICE_INFO = "DeviceInfo";
		public final static String PUSH_REQUEST_PLATFORM = "PushReqPlatform";
		public final static String PUSH_REQUEST_TYPE = "PushType";
		public final static String PUSH_REQUEST_MESSAGE = "PushReqType";
		public final static String PUSH_RESPONSE_PLATFORM = "PushResPlatform";
		public final static String PUSH_RESPONSE_PLATFORM_TIMESTAMP = "PushResPlatformTimestamp";
		public final static String PUSH_RESPONSE_SDK = "PushResponseSdk";
		public final static String PUSH_RESPONSE_SDK_TIMESTAMP = "PushResponseSDKTimestamp";
		public final static String PUSH_RESPONSE_NOT_REGISTRED_COUNT = "nrc";
		public final static String NEWKEY_STATUS_FIRST = "nsf";
		public final static String NEWKEY_STATUS_SECOND = "nss";


		public final static String CAMPAIGN_ID = "cs";
		public final static String SDK_RESPONSE_FLAG = "cl";

		public final static String CAMPAIGN_STATUS = "csf";
		public final static String CAMPAIGN_SEGMENT = "segment";
		public final static String CAMPAIGN_MESSAGE = "msg";
		public final static String CAMPAIGN_MESSAGE2 = "msg2";
		public final static String CAMPAIGN_URI = "url";
		public final static String CAMPAIGN_SOURCE = "src";
		public final static String CAMPAIGN_SDK_COUNT = "csc";
		public final static String CAMPAIGN_PUSH_COUNT = "cpc";
		public final static String CAMPAIGN_OPEN_COUNT = "coc";
		public final static String IMPORTED_PUSH_COUNT = "ic";
		public final static String IMPORTED_FAILURE_COUNT = "ifc";
		public final static String FAILURE_COUNT = "fc";
		public final static String TOTAL_PING_COUNT = "tpc";
		public final static String CAMPAIGN_TITLE = "ct";
		public final static String CAMPAIGN_TITLE2 = "ct2";
		public final static String CAMPAIGN_EXECUTION_TIME = "cet";
		public final static String CAMPAIGN_RUN_TIME = "crt";
		public final static String DYNAMIC_INVALID_COUNT = "dic";
		public final static String INVALID_DEVICE_COUNT = "idc";

		public final static String APILOG_COLUMNFAMILY = "f3";
		public final static String CAMPAIGN_KEY = "ck";
		public final static String HASH_SIGNATURE = "hs";
		public final static String REQUEST_PLATFORM = "rp";
		public final static String REQUEST_IP = "rip";
		public final static String REQUEST_URL = "ru";
		public final static String ERROR_STRING = "es";
		public final static String VALIDATION_ERROR_STRING = "ves";
		public final static String API_HIT = "ah";
		public final static String API_VERSION = "av";
		public final static String JSON_RECEIVED = "jr";
		public final static String IMAGE_URL = "iu";
		public final static String IMAGE_URL2 = "iu2";
		public final static String SOUND_URL = "su";
		public final static String PUSH_TIME = "pt";
		public final static String CLOUDFRONT_URL = "cfu";
		public final static String DISPLAY_PARAMETER = "rq_param";
		public final static String JSON_CAMPAIGN_REPORTS="jsonCr";

		public final static String MESSAGE_TYPE="mt";
		public final static String IN_APP_MESSAGE="iam";
		public final static String IN_APP_TITLE="iat";
		public final static String IN_APP_IMG="iai";
		public final static String IN_APP_BTN_ACTION="iba";
		public final static String IN_APP_BTN_TEXT="ibt";
		public final static String IN_APP_PAGE="iap";
		public final static String IN_APP_TIME="iatm";
		public final static String IN_APP_URL="iaui";
		public final static String EXPIRY_DATE="iaed";

		public final static String SEGMENT_ID="si";
		public final static String DYNAMIC_SEG="ds";
		public final static String SCHEDULED_BUCKET="bkt";
		public final static String SCHEDULE_DAILY_BUCKET="daily";
		public final static String SCHEDULE_WEEKLY_BUCKET="weekly";
		public final static String SCHEDULE_MONTHLY_BUCKET="monthly";

		public final static String SCHEDULE_HOURLY_BUCKET="hourly";
		public static final String SCHEDULED_END_BUCKET = "ebkt";
		public static final String SCHEDULDED_NO_OF_CAMPAIGNS = "no";

		public static final String PROD_IMG_URL = "prodiu";
		public static final String PROD_IMG_URL2 = "prodiu2";
		public static final String OVERRIDE_PARAM = "id";
		public static final String OVERRIDE_PARAM_VALUE = "idVal";
		public static final String PUSHINFO_ROWKEY = "PushInfoRowKey";
		public static final String CAMPAIGN_PACKET = "packet";
		public static final String CAMPAIGN_IMPORTED_PACKET = "importedPacket";
		public static final String CERT_TYPE = "cert_type";
		public static final String NOTIF_ACTION_RADIO = "nar";
		public static final String BUTTON_ACTION_RADIO = "bar";
		public static final String AB_SPLIT = "spl";
		public static final String JSON_STRING = "jsu";


	}

	public static class ImportedColumnKeys {
		public final static String PUSH_INFO_KEY = "PushInfoKey";
		public final static String MESSAGE_KEY = "msgKey";
	}

	public static class PingResponses{
		public final static String NOT_REGISTERED = "NotRegistered";
		public final static String SUCCESSFULL = "Successfull";
	}

	public static class KafkaConstants{
		public final static String Topic = "ResponsePacketDeployment";
		public final static String responseTopic = "ResponsePackets" ;
		public final static String pushInfoTopic = "PushInfoPackets";
		public final static String appInfoTopic = "AppInfoPackets";
		public final static String testTopic = "TestPackets";
		public final static String SDKResponseTopic = KafkaConstants.Topic;
		public final static String RawSDKResponseTopic = "RawSDKResponsePacket" ;
		public final static String PushResponseTopic = KafkaConstants.Topic;
		public final static String RawPushResponseTopic = "Raw_Push_PlatformResponsePacket";
		public static final String GCMResponseTopic = KafkaConstants.Topic;//"SDKResponsePacket" ;
		public final static String DeveloperPushResponseTopic = "DeveloperPushRequests";	}

	public static class KeyValues{
		public final static String OS_ANDROID = "android";
		public final static String OS_IOS = "iOS";
		public final static String OS_WINDOWS = "windows";
		public final static String ANDROID_PACKAGENAME = "sparq_android";
		public final static String IOS_PACKAGENAME = "sparq_iOS";
		public final static String WINDOWS_PACKAGENAME = "sparq_windows";
	}

	public static class PingConstants{
		public final static String PING_TO_ALL_JOB = "pushToAll";
		public final static String PING_TO_TEST_APP_JOB = "pushToTestApp";
		public final static String PING_TO_RECENT_JOB = "pushToRecent";
		public final static String PING_TO_ALL_INTERVAL = "deploymentInterval";
		public final static String PING_TO_TEST_INTERVAL = "testInterval";
		public final static String PING_TO_RECENT_INTERVAL = "recentInterval";
		public final static String PING_PASSWORD_PARAMETER = "password";
		public final static String PING_1 = "ping1";
		public final static String PING_2 = "ping2";
		public final static String PING_3 = "ping3";
		public final static String PING_PASSWORD = "iucabcibciscgisdhwd8wey928yhgwf";
		public final static String SERVICE_PARAMETER = "stopJobName";
		public final static String PING_PUSH_SERVICE = "service";
		public final static String PING_TO_ALL_SERVICE_START = "pushStartAll";
		public final static String PING_TO_ALL_SERVICE_STOP = "pushStopAll";
		public final static String PING_TO_ONE_SERVICE_STOP = "pushStopOne";
		public final static String PING_TO_ONE_SERVICE_JOBNAME = "jobname";
		public final static int PING_1_H=4;
		public final static int PING_1_M=0;
		public final static int PING_2_H=7;
		public final static int PING_2_M=30;
		public final static int PING_3_H=17;
		public final static int PING_3_M=45;
		public final static String PING_R_H_M="0 0/15 * * * ?";

		public final static Long RECENT_ZONE_HIGH_LIMIT = 15 * 60 * 1000L;
		public final static Long RECENT_ZONE_LOW_LIMIT = 45 * 60 * 1000L;


	}

	public static class FieldConstansts{
		public final static String CAMPAIGN_PENDING = "pending";
		public final static String CAMPAIGN_RUNNING = "running";
		public final static String CAMPAIGN_IN_QUEUE = "queued";
		public final static String CAMPAIGN_SCHEDULED = "scheduled";
		public final static String CAMPAIGN_COMPLETED = "completed";
		public final static long NotRegisteredCountLimit = 2;
		public final static int iOS_MESSAGE_THREADS = 10;
	}

	public static class CampaignConstansts{
		public final static String IMPORTED_DISPLAY_PUSH = "rq_message";
		public final static String NORMAL_DISPLAY_PUSH = "rq_disp";
		public final static String PING_PUSH = "rq_ping";
		public final static String PING_MESSAGE = "All"; 
		public final static int PING_TTL = 2419200;

		public final static String RECENT_PING_PUSH = "rq_recent";
		public final static String APP_SECRET = "app_secret";
		public final static String CAMPAIGN_SEGMENT = "segment";
		public final static String CAMPAIGN_MESSAGE = "message";
		public final static String CAMPAIGN_MESSAGE2 = "message2";
		public final static String CAMPAIGN_KEY = "ref";
		public final static String CERT_TYPE = "cert";
		public final static String CAMPAIGN_SOURCE = "src";
		public final static String CAMPAIGN_MODE = "mod";
		public final static String CAMPAIGN_PUSHKEY = "pk";
		public final static String CAMPAIGN_URL = ColumnKeys.CAMPAIGN_URI;
		public final static String CAMPAIGN_PLATFORM = "cp";
		public final static String CAMPAIGN_IMAGE_URL = "ciu";
		public final static String CAMPAIGN_IMAGE_URL2 = "ciu2";
		public final static String IMAGE_URL = "iu";
		public final static String CAMPAIGN_SOUND_URL = "csu";
		public final static String CAMPAIGN_PUSH_ACTION = "act";
		public final static String CAMPAIGN_PUSHTIME = "schedule";
		public final static String DISPLAY_PARAMETER = "rq_param";
		public final static String CAMPAIGN_TITLE = "title";
		public final static String CAMPAIGN_TITLE2 = "title2";
		public final static String MESSAGE_TYPE = "rq_msgtype";
		public final static String DYNAMIC_SEG = "rq_dynamic";
		public final static String SEGMENT_ID = "segmentId";
		public final static String IN_APP_MESSAGE = "rq_in_msg";
		public final static String IN_APP_IMG = "rq_in_img";
		public final static String IN_APP_TITLE = "rq_in_title";
		public final static String BTN_TEXT_PARAMETER = "rq_btn_text";
		public final static String BTN_ACTION_PARAMETER = "rq_btn_action";
		public final static String IP_WHITELIST_MODE = "ipMode";
		public final static String IN_APP_PAGE = "rq_in_pg";
		public final static String IN_APP_TIME = "rq_in_td";
		public final static String EXPIRY_DATE = "rq_ed";
		public final static String IN_APP_URL = "rq_in_url";
		public static final String IMPORTED_FLAG = "ImportedFlag";
		public static final String SCHEDULED_BUCKET = "bktType";
		public static final String SCHEDULED_END_BUCKET = "endBucket";
		public static final String PROD_IMG_URL = "prod_img";
		public static final String LIST_OF_PK = "list";
		public static final String OVERRIDE_PARAM="rq_id";
		public static final String OVERRIDE_MULTIPLE="multiple";
		public static final String OVERRIDE_SINGLE="single";
		public static final String NOTIFICATION_ACTION_RADIO="notif_action_radio";
		public static final String BUTTON_ACTION_RADIO="button_action_radio";
		public static final String AB_SPLIT="split";
	}

	public static class HBaseConfigurationConstansts {

		public static final String IMAGE_INFO_FILE = "imageInfo.txt";
		public final static String HBASE_CONF_FILE ="/etc/hbase/conf/hbase-site.xml";
		public static final boolean SLEEP_NOTIFICATION_THREADS = false;

		public final static boolean deployment = true;
		public final static boolean schedulerFlag = true;


				public final static boolean testTables = true ;
		//public final static boolean testTables = false ;

		public final static boolean testFlag = false ;

		public static final String MASTER_IP = "static.98.153.251.148.clients.your-server.de";
		public static final String MASTER_IP_2 = "static.157.42.251.148.clients.your-server.de";
		public static final String MASTER_IP_3 = "static.232.40.46.78.clients.your-server.de";

		public static final String DYN_SEG_IP = "78.46.40.232";
		public static final String MASTER_RAW_IP = "148.251.153.98";
		public static final String ZOOKEEPER_PORT = "2181";
		public static final String PUSHSERVER_DATASAVING_PORT = "80";
		public static final String PUSHSERVER_SENDINGPUSH_PORT = "85";
		public static final String NGINX_IP = "148.251.42.132:3050";
		public static final String SEGMENT_API = "148.251.42.156:8082";
		//

		//		public static final String iOS_KEYSTORE_PATH = "/home/rq/rocq/sparq-push-server/keys/Certificates/";
		public static final String iOS_KEYSTORE_PATH = "/home/rq/rocq/sparq-push-server/keys/";

		public static final String IMAGE_PATH = "/home/rq/rocq/sparq-push-server/images/";
		public static final String CF_URL = "http://d2fgxlijnufs6m.cloudfront.net";
		public static final String IMG_SERVER = "http://img.rocq.io";
		//		public static final String KAFKA_ZOOKEEPER = "static.206.5.243.136.clients.your-server.de:2181";
		//		public static final String KAFKA_BROKER =  "static.206.5.243.136.clients.your-server.de:9092";

		public static final String KAFKA_ZOOKEEPER = "static.206.5.243.136.clients.your-server.de:2181";
		public static final String KAFKA_BROKER =  "static.206.5.243.136.clients.your-server.de:9092";
		public static final String SDKRESPONSE_SERVER = "static.206.5.243.136.clients.your-server.de";


		//		public final static boolean testFlag = true;
		//		public static final String MASTER_IP = "static.98.153.251.148.clients.your-server.de";
		//		public static final String MASTER_RAW_IP = "148.251.153.98";
		//		public static final String ZOOKEEPER_PORT = "2181";
		//		public static final String PUSHSERVER_DATASAVING_PORT = "5080";
		//		public static final String PUSHSERVER_SENDINGPUSH_PORT = "5081";
		//		public static final String NGINX_IP = "148.251.42.132:3050";
		//		public static final String SEGMENT_API = "148.251.42.156:8082";
		//		public static final String iOS_KEYSTORE_PATH = "/home/rq/rocq/sparq-push-server/keys/";
		//		public static final String IMAGE_PATH = "/home/rq/rocq/sparq-push-server/images/";
		//		public static final String CF_URL = "http://d2nsgqk65xj4r9.cloudfront.net";
		//		public static final String CF_URL = "http://d2fgxlijnufs6m.cloudfront.net";

		////				public final static boolean testFlag = true;
		////      public static final String MASTER_IP = "localhost";

		////	public static final String MASTER_RAW_IP = "127.0.0.1";
		////	public static final String ZOOKEEPER_PORT = "2181";
		////	public static final String PUSHSERVER_DATASAVING_PORT = "5080";
		////	public static final String PUSHSERVER_SENDINGPUSH_PORT = "5081";
		////	public static final String NGINX_IP = "148.251.42.156:3050";
		////	public static final String SEGMENT_API = "148.251.42.156:8082";
		////	public static final String iOS_KEYSTORE_PATH = "/home/dev/pushserver/keys/";		



		//		public final static boolean testFlag = false;
		//		public static final String MASTER_IP = "static.157.42.251.148.clients.your-server.de";
		//		public static final String DYN_SEG_IP = "78.46.40.232";
		//		public static final String DYN_SEG_IP = "static.98.153.251.148.clients.your-server.de";
		//		public static final String MASTER_RAW_IP = "148.251.42.157";
		//		public static final String ZOOKEEPER_PORT = "2181";
		//		public static final String PUSHSERVER_DATASAVING_PORT = "5080";
		//		public static final String PUSHSERVER_SENDINGPUSH_PORT = "5081";
		//		public static final String NGINX_IP = "148.251.42.132:3050";
		//		public static final String SEGMENT_API = "148.251.42.156:8082";
		//		public static final String iOS_KEYSTORE_PATH = "/home/dev/pushserver/keys/Certificates/";
		//		//		public static final String iOS_KEYSTORE_PATH = "/home/dev/pushserver/keys/";
		//		public static final String IMAGE_PATH = "/home/dev/pushserver/images/";
		//		public static final String CF_URL = "http://d310tnh0ulbnpa.cloudfront.net";
		//		public static final String KAFKA_ZOOKEEPER = "static.157.42.251.148.clients.your-server.de:2181";
		//		public static final String KAFKA_BROKER =  "static.157.42.251.148.clients.your-server.de:9092";
		//		public static final String SDKRESPONSE_SERVER = "push.rocq.io";

		//		public static final String KAFKA_ZOOKEEPER = "static.130.122.76.144.clients.your-server.de:2181";
		//		public static final String KAFKA_BROKER =  "static.130.122.76.144.clients.your-server.de:9092";


		//		public static final String SDKRESPONSE_SERVER = "static.130.122.76.144.clients.your-server.de";
		//		public static final String KAFKA_ZOOKEEPER = "static.206.5.243.136.clients.your-server.de:2181";
		//		public static final String KAFKA_BROKER =  "static.206.5.243.136.clients.your-server.de:9092";
		//		public static final String SDKRESPONSE_SERVER = "static.206.5.243.136.clients.your-server.de";


		public static final String HOMESHOP_iOS = "e1d7a07806";
		public static final String HOMESHOP_ANDROID = "33d1575713";
		public static final String SHINE_iOS = "fff585b116";
		public static final String HIND_iOS = "a72f20b5b1";
		public static final String HIND_iOS_2 = "01935da743";
		public static final String TESTAPP = "e3ff4fa654";
		public static final String HOMESHOP_CAMP_STRING = "ab6ec27da6";
		public static final String[] HJ_CAMP_STRING = {"b6d444396d","9580349a26","a5c0026b2d","81bfc64e2a","cff888535d","1fe20edc58","44edb724dd","f88ad4365a",
				"5af0507ed7","4e40fef480","3cf99dc244"};
		public static final String TESTAPP_2 = "2665c38c69";

	}

	public static class ExtraConstants{
		public static final String SHINE_SEGMENT ="ShineJDB";
		public static final String SHINE_REGISTER ="ShineRegister";
		public static final String SHINE_APP ="91c556949f";
		public static final int RETRY_NO= 3;
		public static final int BACKOFF_INITIAL_DELAY = 500;
		public static final int BACKOFF_MAX_DELAY = 5000;
		public static final boolean GCM_RETRY=false;
		public static final boolean GCM_RETRY_SCAN=false;

	}

}


