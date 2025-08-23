"use client";
import { FileTextIcon } from "@radix-ui/react-icons";
import { Flex, Text } from "@radix-ui/themes";
import DownloadButton from "./download-button";
import DeleteButton from "./delete-button";
import ClipboardButton from "./clipboard-button";
import { UploadedData } from "@/lib/localstorage";

export default function DisplayFile({
  uploadedData,
  deleteFile,
}: {
  uploadedData: UploadedData;
  deleteFile: (url: string) => void;
}) {
  const { fileName, token, url } = uploadedData;

  async function deleteRequest(token: string, url: string) {
    const formData = new FormData();
    formData.append("token", token);
    formData.append("delete", "");
    const result = await fetch(url, {
      method: "POST",
      body: formData,
    });
    if (result.ok && result.status === 202) {
      deleteFile(url);
    }
  }

  return (
    <Flex justify="between" p="3">
      <Flex align="center" gap="2">
        <FileTextIcon width="16px" height="16px" />
        <Flex>
          <Text as="p">{fileName}</Text>
        </Flex>
      </Flex>
      <Flex gap="2">
        <ClipboardButton url={url} />
        <DownloadButton href={url} />
        <DeleteButton onClick={() => deleteRequest(token, url)} />
      </Flex>
    </Flex>
  );
}
