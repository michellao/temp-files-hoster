import { Flex } from "@radix-ui/themes";
import Version from "@/components/version";

export default function Footer({ email }: { email?: string }) {
  return (
    <Flex as="div" align="center" mx="10%" pt="3" direction="column">
      <Version />
      {email && (
        <div>
          For DMCA requests, please contact:{" "}
          <a href={`mailto:${email}`}>{email}</a>
        </div>
      )}
    </Flex>
  );
}
