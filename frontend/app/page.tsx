import { Flex, Heading, Text } from "@radix-ui/themes";
import Link from "next/link";
import ContainerApp from "./container-app";
import Footer from "./footer";

export default function Page() {
  const dcmaEmail = process.env.NEXT_PUBLIC_DMCA_EMAIL;
  return (
    <>
      <Flex direction="column" align="center" mb="5">
        <Heading
          as="h1"
          size={{
            initial: "4",
            md: "6",
            xl: "7",
          }}
        >
          Yet another temporary file hoster
        </Heading>
        <Text
          as="p"
          color="gray"
          size={{
            initial: "1",
            md: "2",
            xl: "3",
          }}
        >
          This project is{" "}
          <Link href="https://github.com/michellao/temp-files-hoster">
            open source
          </Link>
          .
        </Text>
      </Flex>
      <main>
        <ContainerApp />
      </main>
      {dcmaEmail && (
        <footer>
          <Footer email={dcmaEmail} />
        </footer>
      )}
    </>
  );
}
