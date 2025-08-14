import { Flex } from "@radix-ui/themes";

export default function Footer({
  email
}: {
  email: string
}) {
  return (
    <Flex as="div" justify="center" mx="10%" pt="2">
        <p>
          For DMCA requests, please contact: <a href={`mailto:${email}`}>{email}</a>
        </p>
    </Flex>
  );
}
