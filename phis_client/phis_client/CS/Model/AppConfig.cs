using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Xml.Serialization;
/**
 *
 * @author Duy Bui
 */
namespace DeIdClient.CS.Model
{
    [Serializable()]
    public class AppConfig
    {
        public string IPAddress = "localhost";
        public string Port = "8080";
        public string Password = "ChangeMe";
        public string InputFolder = @"C:\PatientNotes";
        public string OutputFolder = @"C:\DeidentifiedNotes";
        public string[] CaseSensitiveTerms = getSensitiveTerms();
        public string[] CaseInSensitiveTerms = getInsensitiveTerms();

        private static string[] getInsensitiveTerms()
        {
            string[] terms = new string[]
            {
                "uab",
                "birmingham",
                "alabama"
            };
            return terms;
        }
        private static string[] getSensitiveTerms()
        {
            string[] terms = new string[]
            {
                "MD",
                "MBBS",
                "PhD",
                "PHD",
                "DRN",
                "M.D.",
                "CRNP",
                "LPN",
                "RDMS",
                "RVT",
                "Ph.D",
                "RVTAs",
                "FACS",
                "N.P.",
                "DMD",
                "PA-C",
                "BSN",
                "CWOCN",
                "SCi.D",
                "MSS",
                "MMDD",
                "M.D",
                "PGY2",
                "CNM",
                "PGY-3",
                "PGY3"

            };
            return terms;
        }

        

        public static AppConfig Get()
        {
            AppConfig appConfig=null;
                
            if (!File.Exists("config/AppConfig.xml"))
            {
                System.IO.Directory.CreateDirectory("config");
                System.IO.Directory.CreateDirectory("config/custom_dict/case_sensitive");
                System.IO.Directory.CreateDirectory("config/custom_dict/case_insensitive");
                appConfig = new AppConfig();
                appConfig.Save();

            } else
            {
                using (FileStream ReadFileStream = new FileStream("config/AppConfig.xml", FileMode.Open, FileAccess.Read, FileShare.Read))
                {
                    var serializer = new XmlSerializer(typeof(AppConfig));

                    appConfig = (AppConfig)serializer.Deserialize(ReadFileStream);

                }
            }

            return appConfig;
        }

        public void Save()
        {
            using (var writer = new System.IO.StreamWriter("config/AppConfig.xml"))
            {
                var serializer = new XmlSerializer(typeof(AppConfig));
                serializer.Serialize(writer, this);
                writer.Flush();
            }
        }

        public AppConfig()
        {
            
        }
    }
}
