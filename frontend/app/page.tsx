import Link from "next/link";
import UploadFiles from "@/components/upload-files";
import ManageFiles from "@/components/manage-files";
import { Flex, Heading, Text } from "@radix-ui/themes";

export default function Page() {
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
            This project is <Link href="https://github.com/michellao/temp-files-hoster">open source</Link> and inspire from <Link href="https://0x0.st">0x0.st</Link>
          </Text>
        </Flex>
        <main>
          <UploadFiles/>
          <ManageFiles/>
        </main>
      </>
    );
}
