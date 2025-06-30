'use client';
import { FileTextIcon } from "@radix-ui/react-icons";
import { Flex, Text } from "@radix-ui/themes";
import DownloadButton from "./download-button";
import DeleteButton from "./delete-button";
import ClipboardButton from "./clipboard-button";

export default function DisplayFile({
  fileName,
  url,
}: {
  fileName: string,
  url: string,
}) {
  return (
    <Flex justify="between" p="3">
      <Flex align="center" gap="2">
        <FileTextIcon width="16px" height="16px" />
        <Flex>
          <Text as="p">{ fileName }</Text>
        </Flex>
      </Flex>
      <Flex gap="2">
        <ClipboardButton url={url}/>
        <DownloadButton href={url}/>
        <DeleteButton/>
      </Flex>
    </Flex>
  );
}
