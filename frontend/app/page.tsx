'use client';
import Link from "next/link";
import UploadFiles from "@/components/upload-files";
import ManageFiles from "@/components/manage-files";
import { Flex, Heading, Text } from "@radix-ui/themes";
import { useEffect, useState } from "react";
import { getSize } from "@/storage/tools";

export default function Page() {
  const [fileCount, setFileCount] = useState(0);
  useEffect(() => {
    setFileCount(getSize());
  }, []);

  const handleFileUploaded = () => {
    setFileCount(getSize());
  };

  return (
    <>
      <Flex direction="column" align="center" mb="5">
        <Heading as="h1" size={{
          initial: "4",
          md: "6",
          xl: "7",
        }}>
          Yet another temporary file hoster
        </Heading>
        <Text as="p" color="gray" size={{
          initial: "1",
          md: "2",
          xl: "3",
        }}>
          This project is <Link href="https://github.com/michellao/temp-files-hoster">open source</Link>.
        </Text>
      </Flex>
      <main>
        <UploadFiles onFileUploaded={handleFileUploaded}/>
        <ManageFiles counter={fileCount}/>
      </main>
    </>
  );
}
