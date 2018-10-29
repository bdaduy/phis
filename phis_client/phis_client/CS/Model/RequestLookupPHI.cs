using DeIdClient.CS.Controller;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Security.Cryptography;
using System.Text;
using System.Threading.Tasks;
/**
 *
 * @author Duy Bui
 */
namespace DeIdClient.CS.Model
{
    class RequestLookupPHI
    {
        public string documentId;
        public string patientId;
        public string method;
        public string text;

        public void Encrypt(string password)
        {
            documentId = Encrypt(documentId, password);
            patientId = Encrypt(patientId, password);
            method = Encrypt(method, password);
            text = Encrypt(text, password);
        }
        public string Encrypt(string text, string password)
        {

            if (text == null) return null;
            using (var aesProvider = new AesCryptoServiceProvider())
            {

                aesProvider.Mode = CipherMode.CBC;
                aesProvider.Padding = PaddingMode.PKCS7;

                var Rfc = new Rfc2898DeriveBytes(Encoding.UTF8.GetBytes(password), Encoding.UTF8.GetBytes("phi lookup request"), 1000);
                byte[] bytes = Rfc.GetBytes(16);
                aesProvider.IV = bytes;
                aesProvider.Key = bytes;

                ICryptoTransform encryptor = aesProvider.CreateEncryptor();

                byte[] inputBytes = Encoding.UTF8.GetBytes(text);
                byte[] outputBytes = encryptor.TransformFinalBlock(inputBytes, 0, inputBytes.Length);

                return Convert.ToBase64String(outputBytes);
            }
        }
    }
}
